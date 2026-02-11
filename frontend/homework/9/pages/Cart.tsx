import { useAppDispatch } from "../hooks/useAppDispatch";
import { useAppSelector } from "../hooks/useAppSelector";
import {
  removeFromCart,
  updateQuantity,
} from "../features/cart/cartSlice";
import { useNavigate } from "react-router-dom";
import styles from "./Cart.module.scss";

const Cart: React.FC = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const { items, totalPrice } = useAppSelector(
    (state) => state.cart
  );

  if (items.length === 0) {
    return (
      <div className={styles.container}>
        <div className={styles.empty}>
          <h2>Cart is empty</h2>
          <button
            className={styles.continueBtn}
            onClick={() => navigate("/")}
          >
            Continue Shopping
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className={styles.container}>
      <div className={styles.header}>
        <h2>Your Cart</h2>
        <div />
      </div>

      <div className={styles.items}>
        {items.map((item) => (
          <div className={styles.item} key={item.product.id}>
            <img
              src={item.product.thumbnail}
              className={styles.thumb}
              alt={item.product.title}
            />

            <div className={styles.details}>
              <h3 className={styles.title}>{item.product.title}</h3>
              <p className={styles.price}>${item.product.price}</p>
            </div>

            <div className={styles.controls}>
              <button
                className={styles.qtyBtn}
                onClick={() =>
                  dispatch(
                    updateQuantity({
                      id: item.product.id,
                      quantity: item.quantity - 1,
                    })
                  )
                }
                disabled={item.quantity <= 1}
                aria-label={`Decrease quantity of ${item.product.title}`}
              >
                -
              </button>

              <div className={styles.qtyDisplay}>{item.quantity}</div>

              <button
                className={styles.qtyBtn}
                onClick={() =>
                  dispatch(
                    updateQuantity({
                      id: item.product.id,
                      quantity: item.quantity + 1,
                    })
                  )
                }
                aria-label={`Increase quantity of ${item.product.title}`}
              >
                +
              </button>

              <div>
                <p>
                  Total: ${item.product.price * item.quantity}
                </p>
              </div>

              <button
                className={styles.removeBtn}
                onClick={() => dispatch(removeFromCart(item.product.id))}
                aria-label={`Remove ${item.product.title} from cart`}
              >
                Remove
              </button>
            </div>
          </div>
        ))}
      </div>

      <div className={styles.totalRow}>
        Total Cart Value: ${totalPrice}
      </div>
    </div>
  );
};

export default Cart;
