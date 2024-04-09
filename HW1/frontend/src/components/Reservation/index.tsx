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
import { Loader2, ChevronDown } from 'lucide-react';
import { Collapsible, CollapsibleContent, CollapsibleTrigger } from '@/components/ui/collapsible';
import config from '@/config';

export default function Reservation() {
    const [resCode, setResCode] = useState('');

    const [isTripOpen, setTripIsOpen] = useState(false);

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
            <DialogTrigger id="resButton">Reservation</DialogTrigger>
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
                    <Button
                        id="submitRes"
                        onClick={handleButtonClick}
                        disabled={isPending}
                        className="space-x-3"
                    >
                        <div>Search</div>
                        {isPending && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
                    </Button>
                </div>
                {isError && <DialogDescription>{error.message}</DialogDescription>}
                {data && (
                    <div key={data.uuid} className="space-y-2">
                        <div className="flex flex-row space-x-2">
                            <p>Name:</p>
                            <p id="Name">{data.personName}</p>
                        </div>
                        <div className="flex flex-row space-x-2">
                            <p>Phone Number:</p>
                            <p id="PhoneNumber">{data.phoneNumber}</p>
                        </div>
                        <div className="flex flex-row space-x-2">
                            <p>Payed:</p>
                            <p id="Payed">
                                {data.payed} {data.currencyCode}
                            </p>
                        </div>
                        <Collapsible
                            open={isTripOpen}
                            onOpenChange={setTripIsOpen}
                            className="space-y-2"
                        >
                            <CollapsibleTrigger asChild>
                                <div
                                    id="tripCollapse"
                                    className="flex items-center w-min rounded hover:cursor-pointer hover:bg-accent"
                                >
                                    <p>Trip</p>
                                    <ChevronDown
                                        className={`transition ease-in-out duration-500 ${isTripOpen ? '-rotate-180' : ''}`}
                                    />
                                </div>
                            </CollapsibleTrigger>
                            <CollapsibleContent className="space-y-2">
                                <div className="flex flex-row space-x-2">
                                    <p>Origin City:</p>
                                    <p id="OriginCity">{data.trip.originCity.cityName}</p>
                                </div>
                                <div className="flex flex-row space-x-2">
                                    <p>Destination City:</p>
                                    <p id="DestinationCity">{data.trip.destinationCity.cityName}</p>
                                </div>
                                <div className="flex flex-row space-x-2">
                                    <p>Departure Date:</p>
                                    <p id="DepartureDate">{data.trip.departureDate}</p>
                                </div>
                                <div className="flex flex-row space-x-2">
                                    <p>Arrival Date:</p>
                                    <p id="ArrivalDate">{data.trip.arrivalDate}</p>
                                </div>
                                <div className="flex flex-row space-x-2">
                                    <p>Original Price:</p>
                                    <p id="OriginalPrice">{data.trip.price} EUR</p>
                                </div>
                            </CollapsibleContent>
                        </Collapsible>
                    </div>
                )}
            </DialogContent>
        </Dialog>
    );
}
