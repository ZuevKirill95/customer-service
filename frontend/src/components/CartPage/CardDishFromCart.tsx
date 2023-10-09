import React, {FC} from 'react';
import CardDish from "../DishesPage/CardDish";
import {Button, InputNumber} from "antd";
import {useDispatch} from "react-redux";
import {CloseCircleOutlined} from "@ant-design/icons";
import "./styles/CardDishFromCart.css";
import CartService from "../../services/cartService";
import {IDishFromCart} from "../../types/types";
import {user} from "../../constants/constants";

interface CardDishFromCart {
    dish: IDishFromCart
}
const CardDishFromCart: FC<CardDishFromCart> = ({dish}) => {
    const dispatch = useDispatch();
    const handleUpdateAmount = (dishId: number, quantity: number) => {
        const newQuantity = {
            quantity: quantity,
        };
        CartService.updateQuantity(user?.id, dishId, newQuantity, dispatch)
    }

    const handleRemoveClick = (dishId: number) => {
        CartService.deleteFromCart(user?.id, dishId, dispatch)
    }

    return (
        <div className={"cardDishFromCart"}>
            <CardDish dish={dish} showUseButton={false}>
                <div style={{display: "flex", justifyContent: "space-around", alignItems: "start"}}>
                    <InputNumber
                        value={dish.quantity}
                        min={1}
                        onChange={(quantity: number | null) => handleUpdateAmount(dish.id, quantity ?? 0)}
                    />
                    <Button onClick={() => handleRemoveClick(dish.id)} type="text" size={"large"}
                            icon={<CloseCircleOutlined/>}/>
                </div>
            </CardDish>
        </div>
    );
};

export default CardDishFromCart;