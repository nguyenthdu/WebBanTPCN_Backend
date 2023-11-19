package com.example.backend.services.impl;

import com.example.backend.entities.CartItem;
import com.example.backend.entities.FoodFunction;
import com.example.backend.entities.ShoppingCart;
import com.example.backend.entities.User_;
import com.example.backend.exceptions.AppException;
import com.example.backend.repositories.CartItemRepository;
import com.example.backend.repositories.FoodFunctionRepository;
import com.example.backend.repositories.ShoppingCartRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
	@Autowired
	private final UserRepository userRepository;
	private final ShoppingCartRepository shoppingCartRepository;
	private final FoodFunctionRepository foodFunctionRepository;
	private final CartItemRepository cartItemRepository;
	//biến lưu số lương sản phẩm tạm thời
	
	//TODO: ----------------- ADD FOOD TO CART -----------------
	@Override
	public void addFoodToCart(Long userId, Long foodFunctionId, int quantity) {
		ShoppingCart cart = getOrCreateCartForUser(userId);
		FoodFunction foodFunction = foodFunctionRepository.findById(foodFunctionId)
				.orElseThrow(() -> new AppException("Not found food function with id: " + foodFunctionId, HttpStatus.NOT_FOUND));
		//Nếu số lượng sản phẩm thêm vào giỏ hàng lớn hơn số lượng tồn kho thì báo lỗi
		int tempQuantity = 0;
		for(CartItem cartItem : cart.getCartItems()) {
			if(cartItem.getFoodFunction().getId().equals(foodFunctionId)) {
				tempQuantity += cartItem.getQuantity();
			}
		}
		if(foodFunction.isStatus() && foodFunction.getQuantity() - (quantity + tempQuantity) >= 0) {
			// Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
			Optional<CartItem> existingCartItem = cart.getCartItems().stream()
					.filter(item -> item.getFoodFunction().getId().equals(foodFunctionId))
					.findFirst();
			if(existingCartItem.isPresent()) {
				// Nếu sản phẩm đã có trong giỏ hàng, kiểm tra và giảm số lượng nếu đủ
				if(existingCartItem.get().getQuantity() + quantity <= foodFunction.getQuantity()) {
					existingCartItem.get().setQuantity(existingCartItem.get().getQuantity() + quantity);
				} else {
					// Xử lý khi số lượng vượt quá số lượng tồn kho
//					throw new RuntimeException("Number of products exceeds inventory");
					throw new AppException("Number of products exceeds inventory", HttpStatus.BAD_REQUEST);
				}
			} else {
				// Nếu sản phẩm chưa có trong giỏ hàng, tạo mới một CartItem
				CartItem cartItem = new CartItem();
				cartItem.setShoppingCart(cart);
				cartItem.setFoodFunction(foodFunction);
				cartItem.setQuantity(quantity);
				cart.getCartItems().add(cartItem);
			}
			updateCartTotal(cart);
		} else {
			// Xử lý khi sản phẩm hết hàng hoặc trạng thái là false
//			throw new RuntimeException("product is out of stock or no longer in business");
			throw new AppException("product is out of stock or no longer in business", HttpStatus.BAD_REQUEST);
		}
	}
	
	@Override
	public void removeFoodFromCart(Long userId, Long foodFunctionId) {
//		ShoppingCart cart = getOrCreateCartForUser(userId);
//		cart.getCartItems().removeIf(cartItem -> cartItem.getFoodFunction().getId().equals(foodFunctionId));
//		updateCartTotal(cart);
//		shoppingCartRepository.save(cart);
		ShoppingCart cart = getOrCreateCartForUser(userId);
		Optional<CartItem> existingCartItem = cart.getCartItems().stream()
				.filter(item -> item.getFoodFunction().getId().equals(foodFunctionId))
				.findFirst();
		if(existingCartItem.isPresent()) {
			cart.getCartItems().remove(existingCartItem.get());
			updateCartTotal(cart);
			shoppingCartRepository.save(cart);
		} else {
			throw new AppException("Not found cart item with id: " + foodFunctionId, HttpStatus.NOT_FOUND);
		}
	}
	
	@Override
	public void updateCartItemQuantity(Long cartItemId, int quantity) {
//		CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new RuntimeException("Không tìm thấy mục giỏ hàng"));
//		cartItem.setQuantity(quantity);
//		cartItemRepository.save(cartItem);
		CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new RuntimeException("Not found cart item"));
		FoodFunction foodFunction = cartItem.getFoodFunction();
		//nếu số lượng sản phẩm thêm vào giỏ hàng + vơi số lượng sản phẩm đã có trong giỏ hàng lớn hơn số lượng tồn kho thì báo lỗi
		int tempQuantity = 0;
		for(CartItem item : cartItem.getShoppingCart().getCartItems()) {
			if(item.getFoodFunction().getId().equals(foodFunction.getId())) {
				tempQuantity += item.getQuantity();
			}
		}
		if(foodFunction.isStatus() && foodFunction.getQuantity() - (quantity + tempQuantity) >= 0) {
			cartItem.setQuantity(quantity);
			updateCartTotal(cartItem.getShoppingCart());
			cartItemRepository.save(cartItem);
		} else {
			// Xử lý khi sản phẩm hết hàng hoặc trạng thái là false
			throw new AppException("product is out of stock or no longer in business", HttpStatus.BAD_REQUEST);
		}
	}
	
	@Override
	public ShoppingCart getOrCreateCartForUser(Long userId) {
		Optional<User_> user = userRepository.findById(userId);
		if(user.isPresent()) {
			Optional<ShoppingCart> existingCart = shoppingCartRepository.findByUserId(userId);
			return existingCart.orElseGet(() -> createCartForUser(user.get()));
		} else {
			throw new AppException("Not found user with id: " + userId + " in database", HttpStatus.NOT_FOUND);
		}
	}
	
	@Override
	public ShoppingCart getCartForUser(Long userId) {
		//Xem danh sách cartItem
		Optional<User_> user = userRepository.findById(userId);
		if(user.isPresent()) {
			Optional<ShoppingCart> existingCart = shoppingCartRepository.findByUserId(userId);
			return existingCart.orElseThrow(() -> new RuntimeException("Not found cart for user with id: " + userId));
		} else {
			throw new AppException("Not found user with id: " + userId + " in database", HttpStatus.NOT_FOUND);
		}
	}
	
	private ShoppingCart createCartForUser(User_ user) {
		ShoppingCart cart = new ShoppingCart();
		cart.setUser(user);
		return shoppingCartRepository.save(cart);
	}
	
	private void updateCartTotal(ShoppingCart cart) {
		int totalItem = 0;
		double totalPrice = 0;
		//số lượng sản phẩm có trong giỏ hàng không được nhỏ hơn 1
		for(CartItem cartItem : cart.getCartItems()) {
			if(cartItem.getQuantity() < 1) {
				cartItem.setQuantity(1);
			}
			totalItem += cartItem.getQuantity();
			totalPrice += cartItem.getQuantity() * cartItem.getFoodFunction().getPrice();
		}
		cart.setTotalItem(totalItem);
		cart.setTotalPrice(totalPrice);
		shoppingCartRepository.save(cart);
	}
}
