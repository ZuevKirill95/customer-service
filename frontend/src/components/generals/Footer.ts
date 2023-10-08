import React from 'react';
import "./styles/Footer.css"

const Footer = () => {
    const currentYear = new Date().getFullYear();

    return (
        <footer className={"footer"}>
            <p>
                &copy; {currentYear} Грузинский ресторан
            </p>
        </footer>
    );
}

export default Footer;
