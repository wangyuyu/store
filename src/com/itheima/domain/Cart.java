package com.itheima.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.mail.FetchProfile.Item;

import org.omg.CORBA.PUBLIC_MEMBER;

public class Cart implements Serializable{

	//存放购物车项的集合 key：商品的ID，carItem：购物车项目 使用map集合便于删除单个购物项
	private Map<String, CartItem> map = new LinkedHashMap();
	//总金额
	private Double total = 0.0;
	
	/**
	 * 获取所有的购物车项
	 * @return
	 */
	public Collection<CartItem> getItems() {
		return map.values();
	}
	
	/**
	 * 添加到购物车
	 * @param item
	 */
	public void addCart(CartItem item) {
		//先判断购物车有无商品
		//1.先获取商品的ID
		String pid = item.getProduct().getPid();
		if (map.containsKey(pid)) {
			//有
			//设置该商品之前的购买数量 + 现在的购买数量
			//获取购物车中的商品
			CartItem oItem = map.get(pid);
			oItem.setCount(oItem.getCount() + item.getCount());
			
		} else {
			//无 将购物车项 添加进去
			map.put(pid, item);
		}
		//添加之后修改金额
		total += item.getSubtotal();
		
	}

	/**
	 * 从购物车中删除指定的购物项
	 * @param item
	 */
	public void removeFromCart(String pid) {
		//从集合中删除
		CartItem item = map.remove(pid);
		
		//修改金额
		total -= item.getSubtotal();
	}
	
	/**
	 * 清空购物车
	 */
	public void clearCart() {
		//将集合清空
		map.clear();
		//金额归零
		total = 0.0;
	}
	
	public Map<String, CartItem> getMap() {
		return map;
	}
	public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	
	
}
