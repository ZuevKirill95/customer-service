import React, {useState} from 'react';
import {Form, Input, Radio} from 'antd';
import TextArea from "antd/es/input/TextArea";
import PhoneInput from "react-phone-input-2";
import {IDeliveryInfo} from "../../types/types";

const DeliveryForm: React.FC = () => {
    const [name, setName] = useState('');
    const [address, setAddress] = useState('');
    const [flat, setFlat] = useState('');
    const [floor, setFloor] = useState('');
    const [frontDoor, setFrontDoor] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [description, setDescription] = useState('');

    const onFinish = (values: IDeliveryInfo) => {
        console.log('Received values:', values);
    };

    return (
        <div>
            <h2>Информация о доставке</h2>
            <Form name="deliveryForm" onFinish={onFinish}>
                <Form.Item
                    label="Имя:"
                    name="username"
                    rules={[{required: true, message: 'Пожалуйста, введите ваше имя!'}]}
                >
                    <Input value={name} onChange={(e) => setName(e.target.value)}/>
                </Form.Item>
                <Form.Item
                    label="Адрес:"
                    name="address"
                    rules={[{required: true, message: 'Пожалуйста, введите ваш адрес!'}]}
                >
                    <Input
                        value={address} onChange={(e) => setAddress(e.target.value)}
                        placeholder="Введите улицу и номер дома"
                    />
                </Form.Item>
                <Form.Item
                    label="Номер квартиры:"
                    name="flat"
                    rules={[{required: true, message: 'Пожалуйста, введите номер квартиры'}]}
                >
                    <Input
                        value={flat} onChange={(e) => setFlat(e.target.value)}
                    />
                </Form.Item>
                <Form.Item
                    label="Этаж:"
                    name="floor"
                    rules={[{required: true, message: 'Пожалуйста, введите этаж'}]}
                >
                    <Input
                        value={floor} onChange={(e) => setFloor(e.target.value)}
                    />
                </Form.Item>
                <Form.Item
                    label="Номер подъезда:"
                    name="frontDoor"
                    rules={[{required: true, message: 'Пожалуйста, введите номер подъезда'}]}
                >
                    <Input
                        value={frontDoor} onChange={(e) => setFrontDoor(e.target.value)}
                    />
                </Form.Item>
                <Form.Item
                    label="Телефон:"
                    name="number"
                    rules={[{required: true, message: 'Пожалуйста, введите ваш номер телефона!'}]}
                >
                    <PhoneInput
                        country="ru"
                        onlyCountries={["ru"]}
                        placeholder="+7-xxx-xxx-xx-xx"
                    />
                </Form.Item>
                <Form.Item
                    label="Дополнительная информация:"
                    name="description"
                >
                    <TextArea
                        value={description} onChange={(e) => setDescription(e.target.value)}
                        placeholder="Ваше пожелание"
                    />
                </Form.Item>
            </Form>
        </div>
    );
};

export default DeliveryForm;
