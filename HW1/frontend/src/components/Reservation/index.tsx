import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from '@/components/ui/dialog';
import { Input } from '@/components/ui/input';
import { useMutation } from '@tanstack/react-query';
import React, { useState } from 'react';
import { Button } from '../ui/button';
import { Loader2 } from 'lucide-react';
import { Collapsible, CollapsibleContent, CollapsibleTrigger } from '@/components/ui/collapsible';
import { ChevronUp, ChevronDown } from 'lucide-react';
import config from '@/config';

export default function Reservation() {
    const [resCode, setResCode] = useState('');

    const [isTripOpen, setTripIsOpen] = useState(false);
    const [isOrigCityOpen, setOrigCityIsOpen] = useState(false);
    const [isArrivCityOpen, setArrivCityIsOpen] = useState(false);

    const fetchTodoList = async (uuid: string) => {
        const response = await fetch(`${config.API_URL}reservations/uuid/${uuid}`);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    };

    const { mutate, isPending, isError, data, error } = useMutation({
        mutationKey: ['fetchTodoList'],
        mutationFn: fetchTodoList,
    });

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setResCode(e.target.value);
    };

    const handleButtonClick = () => {
        console.log('Search button clicked');
        console.log('Reservation code:', resCode);
        mutate(resCode);
    };

    return (
        <Dialog>
            <DialogTrigger>Reservation</DialogTrigger>
            <DialogContent className="max-h-[90%] overflow-auto">
                <DialogHeader>
                    <DialogTitle>Reservation Search</DialogTitle>
                </DialogHeader>
                <div className="flex items-center space-x-2">
                    <div className="grid flex-1 gap-2">
                        <Input
                            id="reservation-code"
                            type="text"
                            defaultValue={resCode}
                            onChange={handleInputChange}
                        />
                    </div>
                    <Button onClick={handleButtonClick} disabled={isPending} className="space-x-3">
                        <div>Search</div>
                        {isPending && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
                    </Button>
                </div>
                {isError && <DialogDescription>{error.message}</DialogDescription>}
                {data && (
                    <div key={data.uuid} className="space-y-2">
                        <div>Name: {data.personName}</div>
                        <div>Phone Number: {data.phoneNumber}</div>
                        <div>
                            Payed: {data.payed} {data.currencyCode}
                        </div>
                        <Collapsible
                            open={isTripOpen}
                            onOpenChange={setTripIsOpen}
                            className="space-y-2"
                        >
                            <CollapsibleTrigger asChild>
                                <div className="flex items-center w-min rounded hover:cursor-pointer hover:bg-accent">
                                    <p>Trip</p>
                                    <ChevronDown
                                        className={`transition ease-in-out duration-500 ${isTripOpen ? '-rotate-180' : ''}`}
                                    />
                                </div>
                            </CollapsibleTrigger>
                            <CollapsibleContent className="space-y-2">
                                <div>Origin City: {data.trip.originCity.cityName}</div>
                                <div>Destination City: {data.trip.destinationCity.cityName}</div>
                                <div>Departure Date: {data.trip.departureDate}</div>
                                <div>Arrival Date: {data.trip.arrivalDate}</div>
                                <div>Original Price: {data.trip.price} EUR</div>
                            </CollapsibleContent>
                        </Collapsible>
                    </div>
                )}
            </DialogContent>
        </Dialog>
    );
}
