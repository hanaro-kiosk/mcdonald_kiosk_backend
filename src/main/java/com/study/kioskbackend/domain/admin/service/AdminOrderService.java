package com.study.kioskbackend.domain.admin.service;


import com.study.kioskbackend.domain.admin.dto.OrderEditRequestDto;
import com.study.kioskbackend.domain.admin.dto.OrderResponseDto;
import com.study.kioskbackend.domain.order.entity.Order;
import com.study.kioskbackend.domain.order.repository.OrderRepository;
import com.study.kioskbackend.global.common.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminOrderService {

    private final OrderRepository orderRepository;

    public Page<OrderResponseDto> getOrderList(int page) {
        Pageable paging = PageRequest.of(page, 5, Sort.by( Sort.Order.desc("orderTime")));
        return orderRepository.findAll(paging).map(OrderResponseDto::toDto);
    }

    public ResponseDto<Order> editOrder(Long idx, OrderEditRequestDto orderEditRequestDto) {
        Order order = orderRepository.findById(idx).orElseThrow(()-> new IllegalArgumentException("주문 목록을 찾을 수 없습니다."));

        order = order.editOrder(idx,orderEditRequestDto);
        order = orderRepository.save(order);
        return ResponseDto.success(order);
    }


}
