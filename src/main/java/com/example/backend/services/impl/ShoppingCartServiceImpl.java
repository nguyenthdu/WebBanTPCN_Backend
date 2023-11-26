package com.example.backend.services.impl;

import com.example.backend.dtos.FoodFunctionDto;
import com.example.backend.entities.CartItem;
import com.example.backend.entities.FoodFunction;
import com.example.backend.entities.ShoppingCart;
import com.example.backend.entities.User_;
import com.example.backend.exceptions.AppException;
import com.example.backend.mappers.FoodMapper;
import com.example.backend.repositories.CartItemRepository;
import com.example.backend.repositories.ShoppingCartRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.ShoppingCartService;
import com.example.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
	@Autowired
	private final ShoppingCartRepository shoppingCartRepository;
	private final UserRepository userRepository;
	private final CartItemRepository cartItemRepository;
	private final FoodMapper foodMapper;
	private final UserService userService;
	//biến lưu số lương sản phẩm tạm thời
	//TODO: ----------------- ADD FOOD TO CART -----------------
	
	@Override
	public ShoppingCart addFoodToCart(FoodFunctionDto foodFunctionDto, int quantity, String username) {
		User_ user = userService.findByUsername(username);
		ShoppingCart shoppingCart = user.getShoppingCart();
		if(shoppingCart == null) {
			shoppingCart = new ShoppingCart();
		}
		Set<CartItem> cartItemList = shoppingCart.getCartItems();
		CartItem cartItem = findCartItem(cartItemList, foodFunctionDto.getId());
		FoodFunction foodFunction = foodMapper.toFoodFunction(foodFunctionDto);
//nếu số lượng sản phẩm thêm vào giỏ hàng + vơi số lượng sản phẩm đã có trong giỏ hàng lớn hơn số lượng tồn kho thì báo lỗi
		int itemQuantity = 0;
		if(cartItemList == null) {
			cartItemList = new HashSet<>();
			if(cartItem == null) {
				cartItem = new CartItem();
				cartItem.setFoodFunction(foodFunction);
				cartItem.setQuantity(quantity);
				cartItem.setShoppingCart(shoppingCart);
				cartItemList.add(cartItem);
				cartItemRepository.save(cartItem);
			} else {
				itemQuantity += cartItem.getQuantity() + quantity;
				if(foodFunction.isStatus() && foodFunction.getQuantity() - itemQuantity >= 0) {
					cartItem.setQuantity(itemQuantity);
					cartItemRepository.save(cartItem);
				} else {
					// Xử lý khi sản phẩm hết hàng hoặc trạng thái là false
					throw new AppException("product is out of stock or no longer in business", HttpStatus.BAD_REQUEST);
				}
			}
		} else {
			if(cartItem == null) {
				cartItem = new CartItem();
				cartItem.setFoodFunction(foodFunction);
				cartItem.setQuantity(quantity);
				cartItem.setShoppingCart(shoppingCart);
				cartItemList.add(cartItem);
				cartItemRepository.save(cartItem);
			} else {
				itemQuantity += cartItem.getQuantity() + quantity;
				if(foodFunction.isStatus() && foodFunction.getQuantity() - itemQuantity >= 0) {
					cartItem.setQuantity(itemQuantity);
					cartItemRepository.save(cartItem);
				} else {
					// Xử lý khi sản phẩm hết hàng hoặc trạng thái là false
					throw new AppException("product is out of stock or no longer in business", HttpStatus.BAD_REQUEST);
				}
			}
		}
		updateCartTotal(shoppingCart);
		shoppingCart.setCartItems(cartItemList);
		shoppingCart.setUser(user);
		return shoppingCartRepository.save(shoppingCart);
	}
	
	@Override
	public ShoppingCart updateCartItemQuantity(FoodFunctionDto foodFunctionDto, int quantity, String username) {
		User_ user = userService.findByUsername(username);
		ShoppingCart shoppingCart = user.getShoppingCart();
		Set<CartItem> cartItemList = shoppingCart.getCartItems();
		CartItem cartItem = findCartItem(cartItemList, foodFunctionDto.getId());
		int itemQuantity = 0;
		if(cartItem == null) {
			throw new AppException("Not found cart item with id: " + foodFunctionDto.getId(), HttpStatus.NOT_FOUND);
		} else {
			itemQuantity += cartItem.getQuantity() + quantity;
			if(foodFunctionDto.isStatus() && foodFunctionDto.getQuantity() - itemQuantity >= 0) {
				cartItem.setQuantity(quantity);
				cartItemRepository.save(cartItem);
			} else {
				// Xử lý khi sản phẩm hết hàng hoặc trạng thái là false
				throw new AppException("product is out of stock or no longer in business", HttpStatus.BAD_REQUEST);
			}
		}
		updateCartTotal(shoppingCart);
		shoppingCart.setCartItems(cartItemList);
		shoppingCart.setUser(user);
		return shoppingCartRepository.save(shoppingCart);
	}
	
	@Override
	public ShoppingCart removeFoodFromCart(FoodFunctionDto foodFunctionDto, String username) {
		User_ user = userService.findByUsername(username);
		ShoppingCart shoppingCart = user.getShoppingCart();
		Set<CartItem> cartItemList = shoppingCart.getCartItems();
		CartItem cartItem = findCartItem(cartItemList, foodFunctionDto.getId());
		if(cartItem == null) {
			throw new AppException("Not found cart item with id: " + foodFunctionDto.getId(), HttpStatus.NOT_FOUND);
		} else {
			cartItemList.remove(cartItem);
			cartItemRepository.delete(cartItem);
		}
		updateCartTotal(shoppingCart);
		shoppingCart.setCartItems(cartItemList);
		shoppingCart.setUser(user);
		return shoppingCartRepository.save(shoppingCart);
	}
	
	@Override
	public ShoppingCart getCartByUsername(String username) {
		User_ user = userService.findByUsername(username);
		ShoppingCart shoppingCart = user.getShoppingCart();
		return shoppingCart;
	}
	
	private CartItem findCartItem(Set<CartItem> cartItems, Long foodFunctionId) {
//		return cartItems.stream()
//				.filter(item -> item.getFoodFunction().getId().equals(foodFunctionId))
//				.findFirst()
//				.orElse(null);
		if(cartItems == null) {
			return null;
		}
		CartItem cartItem = null;
		for(CartItem item : cartItems) {
			if(item.getFoodFunction().getId().equals(foodFunctionId)) {
				cartItem = item;
			}
		}
		return cartItem;
	}
	
	@Override
	public void clearShoppingCart(Long id) {
		ShoppingCart shoppingCart = shoppingCartRepository.findById(id)
				.orElseThrow(() -> new AppException("Not found shopping cart with id: " + id, HttpStatus.NOT_FOUND));
		for(CartItem cartItem : shoppingCart.getCartItems()) {
			cartItemRepository.deleteById(cartItem.getId());
		}
		shoppingCart.getCartItems().clear();
		shoppingCart.setTotalItem(0);
		shoppingCart.setTotalPrice(0);
		shoppingCartRepository.save(shoppingCart);
	}
	//Method calculate total price
//	private double totalPrice(Set<CartItem> cartItems) {
//		double totalPrice = 0;
//		for(CartItem item : cartItems) {
//			totalPrice += item.getQuantity() * item.getFoodFunction().getPrice();
//		}
//		return totalPrice;
//	}
//
//	//Method calculate total item
//	private int totalItem(Set<CartItem> cartItems) {
//		int totalItem = 0;
//		for(CartItem item : cartItems) {
//			totalItem += item.getQuantity();
//		}
//		return totalItem;
//	}
	//	@Override
//	public void addFoodToCart(Long userId, Long foodFunctionId, int quantity) {
//		ShoppingCart cart = getOrCreateCartForUser(userId);
//		FoodFunction foodFunction = foodFunctionRepository.findById(foodFunctionId)
//				.orElseThrow(() -> new AppException("Not found food function with id: " + foodFunctionId, HttpStatus.NOT_FOUND));
//		//Nếu số lượng sản phẩm thêm vào giỏ hàng lớn hơn số lượng tồn kho thì báo lỗi
//		int tempQuantity = 0;
//		for(CartItem cartItem : cart.getCartItems()) {
//			if(cartItem.getFoodFunction().getId().equals(foodFunctionId)) {
//				tempQuantity += cartItem.getQuantity();
//			}
//		}
//		if(foodFunction.isStatus() && foodFunction.getQuantity() - (quantity + tempQuantity) >= 0) {
//			Optional<CartItem> existingCartItem = cart.getCartItems().stream()
//					.filter(item -> item.getFoodFunction().getId().equals(foodFunctionId))
//					.findFirst();
//			if(existingCartItem.isPresent()) {
//				// Nếu sản phẩm đã có trong giỏ hàng, kiểm tra và giảm số lượng nếu đủ
//				if(existingCartItem.get().getQuantity() + quantity <= foodFunction.getQuantity()) {
//					existingCartItem.get().setQuantity(existingCartItem.get().getQuantity() + quantity);
//				} else {
//					// Xử lý khi số lượng vượt quá số lượng tồn kho
//					throw new AppException("Number of products exceeds inventory", HttpStatus.BAD_REQUEST);
//				}
//			} else {
//				// Nếu sản phẩm chưa có trong giỏ hàng, tạo mới một CartItem
//				CartItem cartItem = new CartItem();
//				cartItem.setShoppingCart(cart);
//				cartItem.setFoodFunction(foodFunction);
//				cartItem.setQuantity(quantity);
//				cart.getCartItems().add(cartItem);
//			}
//			updateCartTotal(cart);
//		} else {
//			throw new AppException("product is out of stock or no longer in business", HttpStatus.BAD_REQUEST);
//		}
//	}
//
//	@Override
//	public void removeFoodFromCart(Long shoppingCartId, Long cartItemId) {
//		ShoppingCart cart = getOrCreateCartForUser(shoppingCartId);
//		Optional<CartItem> existingCartItem = cart.getCartItems().stream()
//				.filter(item -> item.getFoodFunction().getId().equals(cartItemId))
//				.findFirst();
//		if(existingCartItem.isPresent()) {
//			cart.getCartItems().remove(existingCartItem.get());
//			updateCartTotal(cart);
//			shoppingCartRepository.save(cart);
//		} else {
//			throw new AppException("Not found cart item with id: " + cartItemId, HttpStatus.NOT_FOUND);
//		}
//	}
	
	//	@Override
//	public void updateCartItemQuantity(Long cartItemId, int quantity) {
//		CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new AppException("Not found cart item with id: " + cartItemId, HttpStatus.NOT_FOUND));
//		FoodFunction foodFunction = cartItem.getFoodFunction();
//		//nếu số lượng sản phẩm thêm vào giỏ hàng + vơi số lượng sản phẩm đã có trong giỏ hàng lớn hơn số lượng tồn kho thì báo lỗi
//		int tempQuantity = 0;
//		for(CartItem item : cartItem.getShoppingCart().getCartItems()) {
//			if(item.getFoodFunction().getId().equals(foodFunction.getId())) {
//				tempQuantity += item.getQuantity();
//			}
//		}
//		if(foodFunction.isStatus() && foodFunction.getQuantity() - (quantity + tempQuantity) >= 0) {
//			cartItem.setQuantity(quantity);
//			updateCartTotal(cartItem.getShoppingCart());
//			cartItemRepository.save(cartItem);
//		} else {
//			// Xử lý khi sản phẩm hết hàng hoặc trạng thái là false
//			throw new AppException("product is out of stock or no longer in business", HttpStatus.BAD_REQUEST);
//		}
//	}
//
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
//	@Override
//	public void addFoodToCart(Long userId, Long foodFunctionId, int quantity) {
//		ShoppingCart cart = getOrCreateCartForUser(userId);
//		FoodFunction foodFunction = foodFunctionRepository.findById(foodFunctionId)
//				.orElseThrow(() -> new AppException("Not found food function with id: " + foodFunctionId, HttpStatus.NOT_FOUND));
//		//Nếu số lượng sản phẩm thêm vào giỏ hàng lớn hơn số lượng tồn kho thì báo lỗi
//		int tempQuantity = cart.getCartItems().stream()
//				.filter(item -> item.getShoppingCart().getUser().getId().equals(userId))
//				.filter(item -> item.getFoodFunction().getId().equals(foodFunctionId))
//				.mapToInt(CartItem::getQuantity)
//				.sum();
//		if(foodFunction.isStatus() && foodFunction.getQuantity() - (quantity + tempQuantity) >= 0) {
//			Optional<CartItem> existingCartItem = cart.getCartItems().stream()
//					.filter(item -> item.getShoppingCart().getUser().getId().equals(userId))
//					.filter(item -> item.getFoodFunction().getId().equals(foodFunctionId))
//					.findFirst();
//			if(existingCartItem.isPresent()) {
//				// Nếu sản phẩm đã có trong giỏ hàng, kiểm tra và giảm số lượng nếu đủ
//				if(existingCartItem.get().getQuantity() + quantity <= foodFunction.getQuantity()) {
//					existingCartItem.get().setQuantity(existingCartItem.get().getQuantity() + quantity);
//				} else {
//					// Xử lý khi số lượng vượt quá số lượng tồn kho
//					throw new AppException("Number of products exceeds inventory", HttpStatus.BAD_REQUEST);
//				}
//			} else {
//				// Nếu sản phẩm chưa có trong giỏ hàng, tạo mới một CartItem
//				CartItem cartItem = new CartItem();
//				cartItem.setShoppingCart(cart);
//				cartItem.setFoodFunction(foodFunction);
//				cartItem.setQuantity(quantity);
//				cart.getCartItems().add(cartItem);
//			}
//			updateCartTotal(cart);
//		} else {
//			throw new AppException("Product is out of stock or no longer in business", HttpStatus.BAD_REQUEST);
//		}
//	}
// Cập nhật thông tin giỏ hàng
//	private void updateCartTotal(ShoppingCart cart) {
//		int totalItem = cart.getCartItems().stream()
//				.filter(item -> item.getShoppingCart().getUser().getId().equals(cart.getUser().getId()))
//				.mapToInt(CartItem::getQuantity)
//				.sum();
//		double totalPrice = cart.getCartItems().stream()
//				.filter(item -> item.getShoppingCart().getUser().getId().equals(cart.getUser().getId()))
//				.mapToDouble(item -> item.getFoodFunction().getPrice() * item.getQuantity())
//				.sum();
//		cart.setTotalItem(totalItem);
//		cart.setTotalPrice(totalPrice);
//		// Cập nhật giỏ hàng trong cơ sở dữ liệu nếu cần
//		shoppingCartRepository.save(cart);
//	}
