import React from "react";

type Field = {
	id: string;
	label: string;
	type: string;
	placeholder?: string;
	icon?: string;
	required?: boolean;
	register?: any;
	error?: string;
}

type FormProps = {
	title: string;
	fields: Field[];
	buttonText: string;
	onSubmit: (event: React.FormEvent<HTMLFormElement>) => void;
	responseMessage?: string;
	redirectLinkText: string;
	redirectLinkHref: string;
}

const Form: React.FC<FormProps> = ({
									   title,
									   fields,
									   buttonText,
									   onSubmit,
									   responseMessage,
									   redirectLinkText,
									   redirectLinkHref,
								   }) => {
	return (
		<form onSubmit={onSubmit}>
			<h2 className="text-center text-primary mb-4">{title}</h2>

			{/* Render input fields */}
			{fields.map((field) => (
				<div key={field.id} className="mb-3 position-relative">
					<label htmlFor={field.id} className="form-label">
						{field.label}
					</label>
					<div className="input-group">
						{field.icon && (
							<span className="input-group-text">
                <i className={field.icon}></i>
              </span>
						)}
						<input
							type={field.type}
							className="form-control"
							//id={field.id}
							placeholder={field.placeholder}
							required={field.required}
							{...field.register}
						/>
					</div>
					{field.error && <small className="text-danger">{field.error}</small>} {/* Show validation errors */}
				</div>
			))}

			<div className="d-grid">
				<button type="submit" className="btn btn-primary">
					{buttonText}
				</button>
			</div>
			{responseMessage && <div id="responseMessage" className="mt-3 text-center">{responseMessage}</div>}
			<div className="mb-3 text-center">
				<a href={redirectLinkHref}>{redirectLinkText}</a>
			</div>
		</form>
	);
};

export default Form;
