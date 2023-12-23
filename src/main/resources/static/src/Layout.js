
import React from 'react';

const Layout = ({ children }) => {
    return (
        <div>
            <header>
                {/* Header content */}
            </header>
            <main>
                {children} {/* Main content goes here */}
            </main>
            <footer>
                {/* Footer content */}
            </footer>
        </div>
    );
};

export default Layout;
