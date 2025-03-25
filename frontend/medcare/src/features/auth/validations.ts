import { z } from "zod";

export enum UserRole {
    DOCTOR = "DOCTOR",
    PATIENT = "PATIENT",
    SECRETARY = "SECRETARY",
    ANONYMOUS = "ANONYMOUS",
}
export type User = {
    id: string;
    name: string;
    email: string;
    role: UserRole;
};

const validName = (name: string) => /^\p{L}{2,}$/u.test(name);
const validAmka = (value: string) => /^\d{11}$/.test(value);
const validPersonalID = (value: string) => /^\p{L}{2}\d{6}$/u.test(value);

export const passwordSchema = z
    .string()
    .min(8, "Password must be at least 8 characters long")
    .refine((value) => /[A-Z]/.test(value), {
        message: "Must contain at least one uppercase letter (A-Z)"
    })
    .refine((value) => /[a-z]/.test(value), {
        message: "Must contain at least one lowercase letter (a-z)"
    })
    .refine((value) => /\d/.test(value), {
        message: "Must contain at least one digit (0-9)"
    })
    .refine((value) => /[#?!@$%^&*\-_]/.test(value), {
        message: "Must contain at least one special character (#?!@$%^&*-_)"
    });
export const loginPasswordSchema = z
    .string()
    .min(3,"Password cannot be empty");

export const loginSchema = z.object({
    email: z.string().email("Invalid email format"),
    password: loginPasswordSchema,
});

export const registerSchema = z.object({
    firstName: z.string().refine(validName, {
        message: "First name must contain at least 2 characters from any language",
    }),
    lastName: z.string().refine(validName, {
        message: "Last name must contain at least 2 characters from any language",
    }),
    email: z.string().email("Invalid email format"),
    password: passwordSchema,
    confirmPassword: passwordSchema,
    personalID: z.string().refine(validPersonalID, {
        message: "Personal ID must be exactly 2 letters followed by 6 digits.",
    }),
    amka: z.string().refine(validAmka, {
        message: "AMKA must be exactly 11 digits long.",
    })
}).superRefine((val, ctx) => {
    if (val.password !== val.confirmPassword) {
        ctx.addIssue({
            code: z.ZodIssueCode.custom,
            message: "Confirm  password isn't the same as password",
            path: ['confirmPassword'],
        });
    }
});

// Type inference from Zod schema
export type RegisterSchema = z.infer<typeof registerSchema>;
export type LoginSchema = z.infer<typeof loginSchema>;
