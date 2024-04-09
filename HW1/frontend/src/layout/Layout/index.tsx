import { Outlet, createRootRoute } from '@tanstack/react-router';
import NavBar from '@/layout/NavBar';
import { TanStackRouterDevtools } from '@tanstack/router-devtools';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';

function RootComponent() {
    return (
        <div className="relative flex min-h-screen flex-col">
            <NavBar />
            <Outlet />
            <TanStackRouterDevtools position="bottom-right" />
            <ReactQueryDevtools buttonPosition="bottom-left" />
        </div>
    );
}

export const RootRoute = createRootRoute<{}>({
    component: RootComponent,
});
