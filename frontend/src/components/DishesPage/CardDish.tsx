import React, {FC, useState} from 'react';
import {Button} from "antd";
import "./styles/CardDish.css"
import {IDish} from "../../types/types";
import ModalCardDish from "./ModalCardDish";

interface DishItemProps {
    dish: IDish
    showUseButton: boolean;
    children?: React.ReactNode;
}

const CardDish: FC<DishItemProps> =
    ({
         dish,
         showUseButton,
         children
     }) => {

        const [isModalOpen, setIsModalOpen] = useState(false);

        return (
            <div className={"cardDish"}>
                <img
                    src={dish.urlImage}
                    alt={"Изображение блюда:" + dish.name}
                    style={{width: "370px", borderRadius: "10%", height: "270px", cursor: "pointer"}}
                    onClick={() => {
                        setIsModalOpen(true)
                    }}
                />
                <div className={"cardDish__name"}>{dish.name}</div>
                <div className={"card__container"}>
                    <div className={"cardDish__price"}>{dish.price} ₽</div>
                    {showUseButton && (
                        <Button
                            className={"cardDish__useButton"}
                            type="link"
                            onClick={() => {
                                setIsModalOpen(true)
                            }}
                        >
                            Выбрать
                        </Button>
                    )}
                </div>

                {children}

                <ModalCardDish
                    dish={dish}
                    isModalOpen={isModalOpen}
                    onClose={() => setIsModalOpen(false)}
                />
            </div>
        );
    };

export default CardDish;