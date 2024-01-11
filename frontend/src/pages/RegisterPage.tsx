import React, {FC} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import {Row, Card, Form, Input, Button, message} from 'antd';
import {UserOutlined, MailOutlined, CalendarOutlined, LockOutlined} from '@ant-design/icons';
import authService from '../services/authService';
import {IRegistration} from "../types/types";
import './styles/RegisterPage.css';
import PhoneInput from "react-phone-input-2";


/**
 * Страница регистрации пользователя
 * @constructor
 */
const RegisterPage: FC = () => {
    const [form] = Form.useForm();
    const navigate = useNavigate();
    const currentDate = new Date();

    const onFinish = (values: IRegistration) => {
        authService
            .register(values)
            .then(() => {
                message.success('Вы успешно зарегистрированы');
                navigate('/api/auth/signin');
            })
            .catch((error) => {
                message.error('Ошибка при регистрации');
                console.error(error);
            });
    };

    return (
        <Row className={"registerPage"}>
            <Card className={"registerPage__card"}>
                <div style={{display: 'flex'}}>
                    <div className={"registerPage__card-content"}>
                        <h1 className={"registerPage__card-h1"}>
                            Добро пожаловать!
                        </h1>
                        <p className={"registerPage__card-p"}>
                            Если вы уже зарегистрированы на нашем сайте, то эта форма не для вас.
                        </p>
                        <Link to="/api/auth/signin" style={{marginTop: '20px'}}>
                            <Button
                                type="primary"
                                shape="round"
                                size="large"
                                className={"registerPage__button"}>
                                Авторизироваться
                            </Button>
                        </Link>
                    </div>
                    <div style={{flex: 1, padding: '50px'}}>
                        <h2 className={"registerPage__card-h2"}>
                            Регистрация
                        </h2>
                        <Form form={form} layout="vertical" name="register" onFinish={onFinish}>
                            <Form.Item
                                name="username"
                                rules={[{required: true, message: 'Пожалуйста, введите имя пользователя!'}]}
                            >
                                <Input prefix={<UserOutlined/>} placeholder="Имя пользователя"/>
                            </Form.Item>
                            <Form.Item
                                name="email"
                                rules={[{required: true, message: 'Пожалуйста, введите email!'}]}
                            >
                                <Input prefix={<MailOutlined/>} type="email" placeholder="Email"/>
                            </Form.Item>
                            <Form.Item
                                name="number"
                                validateTrigger={["onBlur"]}
                                rules={[
                                    {
                                        required: true,
                                        message: "Пожалуйста, введите номер телефона",
                                    },
                                ]}
                            >
                                <PhoneInput
                                    country="ru"
                                    onlyCountries={["ru"]}
                                    placeholder="+7-xxx-xxx-xx-xx"
                                />
                            </Form.Item>
                            <Form.Item
                                name="birthdate"
                                rules={[
                                    {required: true, message: 'Пожалуйста, введите дату рождения!'},
                                    ({getFieldValue}) => ({
                                        validator(_, value) {
                                            const selectedDate = new Date(value);

                                            if (selectedDate.getFullYear() >= 1901 && selectedDate <= currentDate) {
                                                return Promise.resolve();
                                            }

                                            if (selectedDate.getFullYear() < 1901) {
                                                return Promise.reject('Год рождения не может быть раньше 1901.');
                                            }

                                            if (selectedDate > currentDate) {
                                                return Promise.reject('Год рождения не может превышать текущую дату');
                                            }
                                        },
                                    }),
                                ]}
                            >
                                <Input prefix={<CalendarOutlined/>} type="date" placeholder="Дата рождения"/>
                            </Form.Item>
                            <Form.Item
                                name="password"
                                rules={[{required: true, message: 'Пожалуйста, введите пароль!'}]}
                            >
                                <Input.Password prefix={<LockOutlined/>} placeholder="Пароль"/>
                            </Form.Item>
                            <Form.Item>
                                <Button type="primary" htmlType="submit" style={{backgroundColor: '#0e4acb'}}>
                                    Зарегистрироваться
                                </Button>
                            </Form.Item>
                        </Form>
                    </div>
                </div>
            </Card>
        </Row>
    );
};

export default RegisterPage;
