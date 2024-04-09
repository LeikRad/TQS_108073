import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';

import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from '@/components/ui/dialog';

import { TripInterface } from '@/interfaces';
import { Button } from '../ui/button';
import FormsModal from './FormsModal';

interface TripsProps {
    readonly tripData: TripInterface;
    readonly currency: string;
    readonly eurRate: number;
}

export default function TripCard({ tripData, currency, eurRate }: TripsProps) {
    console.log(tripData);
    return (
        <Card className="w-6/12">
            <CardHeader>
                <CardTitle>
                    Trip from {tripData.originCity.cityName} to {tripData.destinationCity.cityName}
                </CardTitle>
            </CardHeader>
            <CardContent>
                <div className="flex justify-between">
                    <div className="flex flex-col space-y-2">
                        <p>Departure: {tripData.departureDate}</p>
                        <p>Arrival: {tripData.arrivalDate}</p>
                    </div>
                    <div className="flex flex-col space-y-2 items-end">
                        <p>
                            Price: {(tripData.price * eurRate).toFixed(2)} {currency}
                        </p>
                        <Dialog>
                            <DialogTrigger id={'Book' + tripData.tripId.toString()}>
                                <Button>Book</Button>
                            </DialogTrigger>
                            <DialogContent>
                                <DialogHeader>
                                    <DialogTitle>Form</DialogTitle>
                                </DialogHeader>
                                <DialogDescription>
                                    <p className="text-black">
                                        You are about to book a trip from{' '}
                                        {tripData.originCity.cityName} to{' '}
                                        {tripData.destinationCity.cityName} for{' '}
                                        {(tripData.price * eurRate).toFixed(2)} {currency}
                                    </p>
                                </DialogDescription>
                                <FormsModal tripData={tripData} currency={currency} />
                            </DialogContent>
                        </Dialog>
                    </div>
                </div>
            </CardContent>
        </Card>
    );
}
