import { NotFoundRoute, createRoute } from '@tanstack/react-router';
import HomePage from './pages/Homepage';
import { RootRoute } from './layout/Layout';
// import other pages...

const notFoundRoute = new NotFoundRoute({
    getParentRoute: () => RootRoute,
    component: () => <div>4042 Not Found</div>,
});

const indexRoute = createRoute({
    getParentRoute: () => RootRoute,
    path: '/',
    component: HomePage,
});

const adminRoute = createRoute({
    getParentRoute: () => RootRoute,
    path: '/admin',
    component: HomePage,
});

export const routeTree = RootRoute.addChildren([
    notFoundRoute,
    indexRoute,
    adminRoute,
    // add other routes here...
]);
