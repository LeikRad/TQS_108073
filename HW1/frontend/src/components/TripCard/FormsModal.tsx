import { z } from 'zod';

import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form';

import { Button } from '@/components/ui/button';
import {
    Form,
    FormControl,
    FormDescription,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
} from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { TripInterface } from '@/interfaces';
import { useMutation } from '@tanstack/react-query';
import { Loader2 } from 'lucide-react';
import config from '@/config';

const formSchema = z.object({
    personName: z.string().min(2).max(50),
    phoneNumber: z.string().min(9).max(9),
});

interface TripsProps {
    readonly tripData: TripInterface;
    readonly currency: string;
}

export default function FormsModal({ tripData, currency }: TripsProps) {
    const form = useForm<z.infer<typeof formSchema>>({
        resolver: zodResolver(formSchema),
        defaultValues: {
            personName: '',
            phoneNumber: '',
        },
    });

    const postBooking = async (data: z.infer<typeof formSchema>) => {
        let postData = {
            personName: data.personName,
            phoneNumber: data.phoneNumber,
            trip: {
                tripId: tripData.tripId,
            },
            payed: tripData.price,
            currencyCode: currency,
        };

        console.log(postData);
        try {
            const response = await fetch(`${config.API_URL}reservations`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(postData),
            });
            return response.json();
        } catch (error) {
            throw new Error('Failed to post booking');
        }
    };

    const { mutate, isPending, isError, data, error } = useMutation({
        mutationKey: ['postBooking'],
        mutationFn: postBooking,
    });

    // 2. Define a submit handler.
    function onSubmit(values: z.infer<typeof formSchema>) {
        // Do something with the form values.
        // ✅ This will be type-safe and validated.
        mutate(values);
    }

    return (
        <>
            <Form {...form}>
                <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
                    <FormField
                        control={form.control}
                        name="personName"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel>Name</FormLabel>
                                <FormControl>
                                    <Input placeholder="John Doe" {...field} />
                                </FormControl>
                                <FormMessage />
                            </FormItem>
                        )}
                    />
                    <FormField
                        control={form.control}
                        name="phoneNumber"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel>Phone Number</FormLabel>
                                <FormControl>
                                    <Input placeholder="123456789" {...field} />
                                </FormControl>
                                <FormMessage />
                            </FormItem>
                        )}
                    />
                    <Button type="submit" disabled={isPending}>
                        Submit
                        {isPending && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
                    </Button>
                </form>
            </Form>
            {isError && <FormDescription>{error.message}</FormDescription>}
            {data && <p>Booking successful! Your reservation code is: {data.uuid}</p>}
        </>
    );
}
