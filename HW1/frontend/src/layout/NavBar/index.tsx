import { BusFront } from 'lucide-react';
import { Link } from '@tanstack/react-router';
import { Button } from '@/components/ui/button';
import Reservation from '@/components/Reservation';

const NavBar = () => {
    return (
        <nav className="navbar flex justify-between px-3 py-3 z-30 border h-auto items-center">
            <div className="flex items-center space-x-4">
                <Link to="/">
                    <div className="flex space-x-4 items-center">
                        <BusFront size={32} />
                        <div className="flex flex-col">
                            <p className="font-bold">AdventureArrow</p>
                            <p className="text-sm italic">Pointing Towards Adventure, Across the Country</p>
                        </div>
                    </div>
                </Link>
            </div>
            <div className="flex items-center space-x-4">
                <Reservation />
                <Link to="/admin">
                    <Button>Admin</Button>
                </Link>
            </div>
        </nav>
    );
};

export default NavBar;
