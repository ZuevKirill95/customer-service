import React, {FC, useState} from "react";
import {Button, Card, Input, Form, message} from "antd";
import {
    CalendarOutlined,
    EditOutlined,
    LogoutOutlined,
    MailOutlined,
    SaveOutlined,
} from "@ant-design/icons";
import "react-phone-input-2/lib/style.css";
import PhoneInput from "react-phone-input-2";
import AuthService from "../../services/authService";
import {Link} from "react-router-dom";
import userService from "../../services/userService";
import {IUserResponse} from "../../types/types";
import {useAppDispatch} from "../../hooks";
import './styles/UserProfile.css';

const UserProfile: FC = () => {
    const [isEditing, setIsEditing] = useState<boolean>(false);
    const dispatch = useAppDispatch();
    const user: IUserResponse = JSON.parse(localStorage.getItem("user") || "null");
    const [userData, setUserData] = useState<IUserResponse>(user);

    const handleLogout = () => {
        AuthService.logout();
        message.success("Вы успешно вышли! До свидания!");
        const reloadTime = 1;
        setTimeout(() => {
            window.location.reload();
        }, reloadTime);
    };

    const toggleEditing = () => {
        setIsEditing(!isEditing);
    };

    const handleSave = async (values: IUserResponse) => {
        try {
            await userService.updateUser(user?.id, values, dispatch);
            setIsEditing(false);
            message.success("Данные успешно сохранены!");
            const updatedUser = {...user, ...values};
            localStorage.setItem("user", JSON.stringify(updatedUser));
            setUserData(updatedUser);
        } catch (error) {
            message.error("Произошла ошибка при сохранении данных.");
        }
    };

    const formatPhoneNumber = (phoneNumber: string | undefined) => {
        if (!phoneNumber) return "";
        const countryCode = phoneNumber.slice(0, 1);
        const firstPart = phoneNumber.slice(1, 4);
        const secondPart = phoneNumber.slice(4, 7);
        const thirdPart = phoneNumber.slice(7, 9);
        const fourthPart = phoneNumber.slice(9, 12);

        return `+${countryCode} (${firstPart}) ${secondPart}-${thirdPart}-${fourthPart}`;
    };

    return (
        <div className={"userProfile"}>
            <Card
                hoverable
                className={"card"}
            >
                {isEditing ? (
                    <Form
                        initialValues={userData}
                        onFinish={handleSave}
                        className={"formFields"}
                    >
                        <Form.Item
                            label="Имя"
                            name="username"
                            rules={[
                                {
                                    required: true,
                                    message: "Пожалуйста, введите имя пользователя!",
                                },
                            ]}
                        >
                            <Input/>
                        </Form.Item>
                        <Form.Item label="E-mail" name="email">
                            <Input
                                prefix={<MailOutlined/>}
                                disabled={isEditing}
                            />
                        </Form.Item>
                        <Form.Item
                            label="Дата рождения"
                            name="dateOfBirth"
                            rules={[
                                {
                                    required: true,
                                    message: "Пожалуйста, введите дату рождения!",
                                },
                            ]}
                        >
                            <Input prefix={<CalendarOutlined/>} type="date" placeholder="Дата рождения"/>
                        </Form.Item>
                        <Form.Item
                            label="Номер телефона"
                            name="number"
                            validateTrigger={["onBlur"]}
                            rules={[
                                {
                                    required: true,
                                    message: "Введите номер телефона",
                                },
                            ]}
                        >
                            <PhoneInput
                                country="ru"
                                onlyCountries={["ru"]}
                                placeholder="+7-xxx-xxx-xx-xx"
                            />
                        </Form.Item>
                        <Form.Item>
                            <Button type="primary" htmlType="submit">
                                <SaveOutlined/>
                                Сохранить
                            </Button>
                        </Form.Item>
                    </Form>
                ) : (
                    <div className={"fields"}>
                        <p>
                            Имя: <span>{user.username}</span>
                        </p>
                        <p>
                            E-mail: <span>{user.email}</span>
                        </p>
                        <p>
                            Дата рождения: <span>{user.dateOfBirth.toString()}</span>
                        </p>
                        <p>
                            Номер телефона: <span> {formatPhoneNumber(user.number)}</span>
                        </p>
                    </div>
                )}

                <Button
                    className={"buttonEditing"}
                    onClick={toggleEditing}
                >
                    {isEditing ? <span>Отменить</span> : <EditOutlined/>}
                </Button>
                <Link to="/">
                    <Button
                        icon={<LogoutOutlined/>}
                        onClick={handleLogout}
                        className={"buttonLogout"}
                    />
                </Link>
            </Card>
        </div>
    );
};

export default UserProfile;