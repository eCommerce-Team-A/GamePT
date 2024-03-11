package com.example.gamePT.domain.orderItem.service;

import com.example.gamePT.domain.cartItem.entity.CartItem;
import com.example.gamePT.domain.cartItem.service.CartItemService;
import com.example.gamePT.domain.chatLog.entity.ChatLog;
import com.example.gamePT.domain.chatLog.service.ChatLogService;
import com.example.gamePT.domain.chatthingRoom.entity.ChattingRoom;
import com.example.gamePT.domain.chatthingRoom.serivce.ChattingRoomService;
import com.example.gamePT.domain.course.entity.Course;
import com.example.gamePT.domain.course.repository.CourseRepository;
import com.example.gamePT.domain.course.service.CourseService;
import com.example.gamePT.domain.order.entity.OrderEntity;
import com.example.gamePT.domain.order.service.OrderService;
import com.example.gamePT.domain.orderItem.controller.OrderItemController;
import com.example.gamePT.domain.orderItem.entity.OrderItem;
import com.example.gamePT.domain.orderItem.repository.OrderItemRepository;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    private final CartItemService cartItemService;
    private final OrderService orderService;
    private final UserService userService;

    private final ChattingRoomService chattingRoomService;
    private final ChatLogService chatLogService;
    private final CourseService courseService;

    private final CourseRepository courseRepository;

    @Transactional
    public OrderItemController.OrderItemsCreateResponse createByCart(List<String> cartItem_ids, Integer total_price) {

        SiteUser buyUser = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if(buyUser.getPoint() < total_price){
            return new OrderItemController.OrderItemsCreateResponse(false,"잔액 부족");
        }

        // 전체 주문 생성
        OrderEntity orderEntity = orderService.create(buyUser, total_price);

        // 주문 아이템 생성
        for(String i : cartItem_ids){
            Long id = Long.parseLong(i);

            CartItem ci = this.cartItemService.findById(id);

            if(buyUser.getUsername().equals(ci.getCourse().getAuthor().getUsername())){
                return new OrderItemController.OrderItemsCreateResponse(false,"자신의 강의는 구매 불가");
            }

            if(!ci.getBuyer().getUsername().equals(buyUser.getUsername())){
                return new OrderItemController.OrderItemsCreateResponse(false,"잘못된 접근");
            }

            OrderItem oi = OrderItem.builder()
                    .course(ci.getCourse())
                    .orderEntity(orderEntity)
                    .name(ci.getCourse().getName())
                    .price(ci.getCourse().getPrice())
                    .gameCategoryname(ci.getCourse().getGameCategoryname())
                    .build();

            orderItemRepository.save(oi);

            // 생성 후 채팅창 만들기
            ChattingRoom cr = chattingRoomService.getChattingRoomsByBuyUserAndExpert(buyUser,ci.getCourse().getAuthor());

            if(cr == null){
                cr = chattingRoomService.create(buyUser, ci.getCourse().getAuthor());
            }

            // 전문가 채팅 보내기
            ChatLog cl = ChatLog.builder()
                    .chattingRoom(cr)
                    .content(ci.getCourse().getName()+"을 구매해주셔서 감사합니다. \n 자세한 내용은 여기서 나누자~")
                    .isCheck(false)
                    .sender(ci.getCourse().getAuthor())
                    .build();

            chatLogService.save(cl);

            // 장바구니 삭제
            cartItemService.deleteCartItemBy(ci.getId(),buyUser);

        }
        buyUser = buyUser.toBuilder().point(buyUser.getPoint() - total_price).build();
        userService.save(buyUser);

        return new OrderItemController.OrderItemsCreateResponse(true,"구매 완료");
    }

    public OrderItemController.OrderItemsCreateResponse createByCourseId(Long courseId) {

        SiteUser buyUser = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        Course course = courseService.findCourseById(courseId);

        if(buyUser.getUsername().equals(course.getAuthor().getUsername())){
            return new OrderItemController.OrderItemsCreateResponse(false,"자신의 강의는 구매 불가");
        }

        if(buyUser.getPoint() < course.getPrice()){
            return new OrderItemController.OrderItemsCreateResponse(false,"잔액 부족");
        }

        // 전체 주문 생성
        OrderEntity orderEntity = orderService.create(buyUser, course.getPrice());

        OrderItem oi = OrderItem.builder()
                .course(course)
                .orderEntity(orderEntity)
                .name(course.getName())
                .price(course.getPrice())
                .gameCategoryname(course.getGameCategoryname())
                .build();

        orderItemRepository.save(oi);

        // 생성 후 채팅창 만들기
        ChattingRoom cr = chattingRoomService.getChattingRoomsByBuyUserAndExpert(buyUser,course.getAuthor());

        if(cr == null){
            cr = chattingRoomService.create(buyUser, course.getAuthor());
        }

        // 전문가 채팅 보내기
        ChatLog cl = ChatLog.builder()
                .chattingRoom(cr)
                .content(course.getName()+"을 구매해주셔서 감사합니다. \n 자세한 내용은 여기서 나누자~")
                .isCheck(false)
                .sender(course.getAuthor())
                .build();

        chatLogService.save(cl);

        buyUser = buyUser.toBuilder().point(buyUser.getPoint() - course.getPrice()).build();
        userService.save(buyUser);



        List<SiteUser> buyerList = course.getBuyerList();
        buyerList.add(buyUser);
        course = course.toBuilder().buyerList(buyerList).build();
        courseRepository.save(course);

        return new OrderItemController.OrderItemsCreateResponse(true,"구매 완료");
    }
}
