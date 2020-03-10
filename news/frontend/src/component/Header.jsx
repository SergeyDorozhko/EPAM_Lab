import React from 'react';

export default function Header() {
    return (
        <nav class="navbar navbar-expand-sm bg-primary navbar-dark fixed-top ">
            <div class="container">

                <a class="navbar-brand">News Portal</a>

                <ul class="nav-item">
                    <button type="button" class="btn">EN</button>
                    <button type="button" class="btn">RU</button>
                    <button type="button" class="btn">FR</button>
                </ul>
                <ul class="nav justify-content-right">
                    <button type="button" class="btn">Log in</button>
                    <button type="button" class="btn">Register</button>
                </ul>
            </div>
        </nav>
    )

}