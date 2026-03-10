import { Outlet, Navigate } from 'react-router-dom';
import { Header } from './Header';
import { Footer } from './Footer';

export function PageLayout() {
    return (
        <>
        <Header />
        <Outlet />
        <Footer />
        </>
    );
}
