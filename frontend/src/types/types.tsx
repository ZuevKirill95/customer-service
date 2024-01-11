export interface IDish {
    id: number;
    name: string;
    description: string;
    urlImage: string;
    category: {
        id: number;
        category: string;
    };
    price: number;
    weight: number;
}

export interface IDishFromCart extends IDish {
    quantity: number;
}

export interface IDishFromOrderResponse {
    dishId: number;
    dishName: string;
    quantity: number;
}

export interface IDishFromOrderHistory {
    orderId: number;
    dishId: number;
    dishName: string;
    quantity: number;
}

export interface IDeliveryInfo {
    clientName: string;
    clientPhoneNumber: string;
    description: string;
    address: string;
    flat: number;
    floor: number;
    frontDoor: number;
}

export interface IOrderFromHistory extends IDeliveryInfo {
    id: number;
    clientId: number;
    totalPrice: number;
    listDishesFromOrder: IDishFromOrderHistory[];
    status: string;
    orderTime: string;
    refusalReason: string | null;
}

export interface IOrderResponse extends IDeliveryInfo {
    clientId: number;
    totalPrice: number;
    weight: number;
    listDishesFromOrder: IDishFromOrderResponse[];
}

export interface IUser {
    id: number;
    username: string;
    number: string;
    birthdate: Date;
    email: string;
}

export interface IUserResponse {
    refresh_token: Boolean;
    accessToken: string;
    id: number;
    username: string;
    number: string;
    birthdate: Date;
    email: string;
    roles: string;
}

export interface IRegistration {
    username: string;
    number: string;
    birthdate: Date;
    email: string;
    password: string;
}

export interface ILogin {
    username: string;
    password: string;
}



