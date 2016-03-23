package com.mason.shop.order.action;

import java.io.IOException;
import java.util.Date;

import org.apache.struts2.ServletActionContext;

import com.mason.shop.cart.vo.Cart;
import com.mason.shop.cart.vo.CartItem;
import com.mason.shop.order.service.OrderService;
import com.mason.shop.order.vo.Order;
import com.mason.shop.order.vo.OrderItem;
import com.mason.shop.user.vo.User;
import com.mason.shop.util.PageBean;
import com.mason.shop.util.PaymentUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 订单模块的Action
 * @author Mason
 *
 */
public class OrderAction extends ActionSupport implements ModelDriven<Order>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	//模型驱动使用的对象,订单对象
	private Order order = new Order();
	@Override
	public Order getModel() {
		return order;
	}
	
	//接收单击"我的订单"时传来的page值
	private Integer page;
	public void setPage(Integer page) {
		this.page = page;
	}
	
	//接收支付通道的编码:
	private String pd_FrpId;
	public void setPd_FrpId(String pd_FrpId) {
		this.pd_FrpId = pd_FrpId;
	}
	
	//接受付款成功后的响应的数据
	private String r6_Order;
	private String r3_Amt;	
	public void setR6_Order(String r6_Order) {
		this.r6_Order = r6_Order;
	}
	public void setR3_Amt(String r3_Amt) {
		this.r3_Amt = r3_Amt;
	}

	//注入OrderService
	private OrderService orderService;
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	//生成订单的方法
	public String save(){
		//1.保存到数据库中
		//获取当前购物车
		Cart cart = (Cart) ServletActionContext.getRequest().getSession().getAttribute("cart");
		if(cart == null){
			//未获取到购物车对象，说明还没有进行购物
			this.addActionError("您还没有购物!");
			return "msg";
		}
		
		//获取当前用户
		User existUser = (User) ServletActionContext.getRequest().getSession().getAttribute("existUser");
		if(existUser == null){
			this.addActionError("您还没有登录!请先登录~");
			return "login";
		}
		
		/*订单中的信息*/
		/*	private Integer oid;
			private Double total;
			private Date ordertime;
			private Integer state;
			private String name;
			private String addr;
			private String phone;
			//订单所属的用户:
			private User user;
			//订单中所属的多个订单项
			private Set<OrderItem> orderItems = new HashSet<OrderItem>();
		 * */
		//设置订单中的信息
		order.setTotal(cart.getTotal());
		order.setOrdertime(new Date());
		order.setState(1);	//1.未付款	2.已付款，未发货 		3.已发货，未确认收货	   4.交易完成
		order.setName(existUser.getName());
		order.setAddr(existUser.getAddr());
		order.setPhone(existUser.getPhone());
		order.setUser(existUser);
			//设置订单中的订单项:订单项为中间表中的内容
		for (CartItem cartItem : cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setCount(cartItem.getCount());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setOrder(order);
			orderItem.setSubtotal(cartItem.getSubtotal());
			
			order.getOrderItems().add(orderItem);
		}
		//判断当前购物车中的东西是否已经被提交过,没提交过则保存订单信息
		if(!cart.isSubmited()){
			orderService.save(order);
			cart.setSubmited(true);
		}
		cart.clearCart();
		//2. 将订单对象显示到页面上
		//通过值栈的方式来进行显示:因为Order显示的是模型驱动的对象
		return "saveSuccess";
	}
	
	//根据用户的id查询用户订单
	public String findByUid(){
		//根据用户的id进行查询
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("existUser");
		//调用sevice
		PageBean<Order> pageBean = orderService.findByPageUid(user.getUid(),page);
		//将分页数据显示到页面上
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findByUidSuccess";
	}
	
	//根据订单号查询订单
	public String findByOid(){
		order = orderService.findByOid(order.getOid());
		return "findByOidSuccess";
	}
	
	//为订单付款的方法
	public String payOrder() throws IOException{
		//修改订单
		Order currOrder = orderService.findByOid(order.getOid());
		currOrder.setAddr(order.getAddr());
		currOrder.setName(order.getName());
		currOrder.setPhone(order.getPhone());
		System.out.println("mason:" + order.getAddr());
		orderService.update(currOrder);
		
		//为订单付款
		String p0_Cmd = "Buy";	//业务类型
		String p1_MerId = "10001126856"; //商户编号
		String p2_Order = order.getOid().toString(); //订单编号
		String p3_Amt = "0.01"; //付款金额，测试使用一分钱
		String p4_Cur = "CNY";	//交易的币种
		String p5_Pid = "";	//商品的名称
		String p6_Pcat = ""; //商品的种类
		String p7_Pdesc = ""; //商品的描述
		String p8_Url = "http://localhost:8080/shop/order_callBack.action"; //支付成功后跳转的页面的路径
		String p9_SAF = "";
		String pa_MP = "";
		String pd_FrpId = this.pd_FrpId; //支付通道编码
		String pr_NeedResponse = "1";	//应答机制
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);
		
		//向易宝出发:
		StringBuffer stringBuffer = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
		stringBuffer.append("p0_Cmd=").append(p0_Cmd).append("&");
		stringBuffer.append("p1_MerId=").append(p1_MerId).append("&");
		stringBuffer.append("p2_Order=").append(p2_Order).append("&");
		stringBuffer.append("p3_Amt=").append(p3_Amt).append("&");
		stringBuffer.append("p4_Cur=").append(p4_Cur).append("&");
		stringBuffer.append("p5_Pid=").append(p5_Pid).append("&");
		stringBuffer.append("p6_Pcat=").append(p6_Pcat).append("&");
		stringBuffer.append("p7_Pdesc=").append(p7_Pdesc).append("&");
		stringBuffer.append("p8_Url=").append(p8_Url).append("&");
		stringBuffer.append("p9_SAF=").append(p9_SAF).append("&");
		stringBuffer.append("pa_MP=").append(pa_MP).append("&");
		stringBuffer.append("pd_FrpId=").append(pd_FrpId).append("&");
		stringBuffer.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
		stringBuffer.append("hmac=").append(hmac);
		
		//重定向到易宝
		ServletActionContext.getResponse().sendRedirect(stringBuffer.toString());
		
		return NONE;
	}
	
	//付款成功后的转向
	/**
	 * 实际上的流程中，从易宝返回的数据中有代表付款状态的代码，1代表易宝经过了网银但未收到网银对其的付款。2代表易宝已经
	 * 收到了付款。两种情况下易宝平台都回回调至p8_Url的地址中，即通知原网站平台.
	 * 而网站这端接收到了易宝的通知后应当对从易宝传回的数据进行接收，其中包括付款状态码。
	 * 之后先根据接收到的pN_xxx参数再次应用PaymentUtil中的加密算法得到一次hmac码，比对两次的hmac是否相同，如果相同
	 * 则支付信息有效。此外需要通过判断状态码是否为2来判断是否完成支付.
	 * 
	 * 这里简化了流程，非公网ip地址只能接受到状态码1.这里只要接受到易宝的通知，即视为完成支付
	 */
	public String callBack(){
		//修改订单状态:修改状态为已经付款
		Order currOrder = orderService.findByOid(Integer.parseInt(r6_Order));
		currOrder.setState(2);
		orderService.update(currOrder);
		//在页面显示付款成功的一些信息
		this.addActionMessage("订单付款成功: 订单的编号:" + r6_Order + " 付款的金额:" + r3_Amt);
		return "msg";
	}
	
}
