package com.shoppingcart.apps.dao;

import java.util.List;

import com.shoppingcart.apps.model.CartInfo;
import com.shoppingcart.apps.model.OrderDetailInfo;
import com.shoppingcart.apps.model.OrderInfo;
import com.shoppingcart.apps.model.PaginationResult;

public interface OrderDAO {
	 
    public void saveOrder(CartInfo cartInfo);
 
    public PaginationResult<OrderInfo> listOrderInfo(int page,
            int maxResult, int maxNavigationPage);
    
    public OrderInfo getOrderInfo(String orderId);
    
    public List<OrderDetailInfo> listOrderDetailInfos(String orderId);
 
}
