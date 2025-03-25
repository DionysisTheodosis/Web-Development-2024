import { z } from "zod";
import {loginSchema, registerSchema} from "./validations.ts";
export enum UserRole {
	DOCTOR = "DOCTOR",
	PATIENT = "PATIENT",
	SECRETARY = "SECRETARY",
	ANONYMOUS = "ANONYMOUS",
}

export type RegisterData = z.infer<typeof registerSchema>;
export type LoginData = z.infer<typeof loginSchema>;



export type LoginSchema = z.infer<typeof loginSchema>;
export type PasswordSchema = z.infer<typeof loginSchema.shape.password>;
