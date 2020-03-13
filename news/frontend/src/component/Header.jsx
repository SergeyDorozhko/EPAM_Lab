import React from 'react';

export default function Header() {
    return (
        <nav className="navbar navbar-expand-sm bg-primary navbar-dark fixed-top ">
            <div className="container">

                <a className="navbar-brand" href="/">News Portal</a>

                <ul className="nav-item">
                    <button type="button" className="btn">EN</button>
                    <button type="button" className="btn">RU</button>
                    <button type="button" className="btn">FR</button>
                </ul>
                <ul className="nav justify-content-right">
                    <button type="button" className="btn">Log in</button>
                    <button type="button" className="btn">Register</button>
                </ul>
            </div>
        </nav>
    )

}