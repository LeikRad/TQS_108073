import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from '@/components/ui/select';
import { useQuery } from '@tanstack/react-query';
import { useState } from 'react';
import TripCard from '@/components/TripCard';
import config from '@/config';

export default function HomePage() {
    const [originCity, setOriginCity] = useState('');
    const [destinationCity, setDestinationCity] = useState('');
    const [currency, setCurrency] = useState('EUR');
    const [eurRate, setEurRate] = useState(1);

    const fetchCityList = async () => {
        console.log('Fetching city list');
        const response = await fetch(`${config.API_URL}cities`);
        const data = await response.json();
        return data;
    };

    const { data: cityData } = useQuery({
        queryKey: ['cities'],
        queryFn: fetchCityList,
    });

    const fetchCurrencyList = async () => {
        const response = await fetch(`${config.API_URL}currencies`);
        const data = await response.json();
        return data;
    };

    const { data: currencyData } = useQuery({
        queryKey: ['currencies'],
        queryFn: fetchCurrencyList,
    });

    const fetchTripList = async () => {
        let url = `${config.API_URL}trips`;
        if (originCity) {
            url = url.concat(`?originCity=${originCity}`);
        }
        if (destinationCity) {
            url = url.includes('?')
                ? url.concat(`&destinationCity=${destinationCity}`)
                : url.concat(`?destinationCity=${destinationCity}`);
        }
        console.log('Fetching trip list:', url);
        const response = await fetch(url);
        const data = await response.json();
        return data;
    };

    const { data: tripData } = useQuery({
        queryKey: ['trips', originCity, destinationCity],
        queryFn: fetchTripList,
    });

    return (
        <div className="m-16 flex flex-col items-center space-y-6 justify-center">
            <p className="text-6xl">Trips List</p>
            <div className="h-0.5 rounded w-full bg-gradient-to-r from-transparent via-slate-900 to-transparent" />
            <div className="flex flex-col space-y-4 w-9/12 space-y-20">
                <div className="flex flex-row justify-between">
                    <div className="flex flex-row space-x-4">
                        <div>
                            <Select>
                                <div className="flex items-center space-x-2">
                                    <p>Origin City</p>
                                    <SelectTrigger className="w-[240px]">
                                        <SelectValue placeholder="" />
                                    </SelectTrigger>
                                </div>
                                <SelectContent className="max-h-[250px] overflow-auto">
                                    <SelectItem
                                        value="All"
                                        onMouseDown={() => {
                                            setOriginCity('');
                                        }}
                                    >
                                        All
                                    </SelectItem>
                                    {cityData?.map((city: any) => (
                                        <SelectItem
                                            value={city.cityId}
                                            key={city.cityId}
                                            onMouseDown={() => {
                                                setOriginCity(city.cityName);
                                            }}
                                        >
                                            {city.cityName}
                                        </SelectItem>
                                    ))}
                                </SelectContent>
                            </Select>
                        </div>
                        <div>
                            <Select>
                                <div className="flex items-center space-x-2">
                                    <p>Destination City</p>
                                    <SelectTrigger className="w-[240px]">
                                        <SelectValue placeholder="" />
                                    </SelectTrigger>
                                </div>
                                <SelectContent className="max-h-[250px] overflow-auto">
                                    <SelectItem
                                        value="All"
                                        onMouseDown={() => setDestinationCity('')}
                                    >
                                        All
                                    </SelectItem>
                                    {cityData?.map((city: any) => (
                                        <SelectItem
                                            value={city.cityId}
                                            key={city.cityId}
                                            onMouseDown={() => setDestinationCity(city.cityName)}
                                        >
                                            {city.cityName}
                                        </SelectItem>
                                    ))}
                                </SelectContent>
                            </Select>
                        </div>
                    </div>
                    <div>
                        <Select>
                            <div className="flex items-center space-x-2">
                                <p>Origin City</p>
                                <SelectTrigger className="w-auto">
                                    <SelectValue placeholder="EUR" />
                                </SelectTrigger>
                            </div>
                            <SelectContent className="max-h-[250px] overflow-auto">
                                {currencyData?.map((currency: any) => (
                                    <SelectItem
                                        value={currency.id}
                                        key={currency.id}
                                        onMouseDown={() => {
                                            setCurrency(currency.id);
                                            setEurRate(currency.eurRate);
                                        }}
                                    >
                                        {currency.id}
                                    </SelectItem>
                                ))}
                            </SelectContent>
                        </Select>
                    </div>
                </div>
                <div className="flex flex-col space-y-4 justify-center items-center">
                    {tripData?.map((trip: any) => (
                        <TripCard
                            key={trip.tripId}
                            tripData={trip}
                            currency={currency}
                            eurRate={eurRate}
                        />
                    ))}
                </div>
            </div>
        </div>
    );
}
