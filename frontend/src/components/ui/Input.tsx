import React from 'react';

interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  label?: string;
  error?: string;
}

const Input: React.FC<InputProps> = ({
  label,
  error,
  className = '',
  ...props
}) => {
  return (
    <div className="w-full">
      {label && (
        <label className="block text-sm font-medium mb-2 tracking-tight">
          {label}
        </label>
      )}
      <input
        className={`w-full border-b-2 border-foreground bg-background text-foreground px-0 py-3 text-base placeholder-muted-foreground focus:outline-none focus:border-b-4 transition-all duration-100 placeholder-italic ${className}`}
        {...props}
      />
      {error && (
        <p className="text-xs mt-1 text-muted-foreground">
          {error}
        </p>
      )}
    </div>
  );
};

export default Input;
