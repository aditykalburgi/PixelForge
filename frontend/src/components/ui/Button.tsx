import React from 'react';

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: 'primary' | 'secondary' | 'ghost';
  size?: 'sm' | 'md' | 'lg';
  children: React.ReactNode;
}

const Button: React.FC<ButtonProps> = ({
  variant = 'primary',
  size = 'md',
  className = '',
  children,
  ...props
}) => {
  const baseStyles = 'font-sans font-medium tracking-widest uppercase transition-all duration-100 focus-visible:outline focus-visible:outline-3 focus-visible:outline-offset-3';

  const sizeStyles = {
    sm: 'px-4 py-2 text-xs',
    md: 'px-6 py-3 text-sm',
    lg: 'px-8 py-4 text-sm',
  };

  const variantStyles = {
    primary: 'bg-foreground text-background border border-foreground hover:bg-background hover:text-foreground hover:border-foreground',
    secondary: 'bg-transparent text-foreground border-2 border-foreground hover:bg-foreground hover:text-background',
    ghost: 'bg-transparent text-foreground border-none hover:underline',
  };

  return (
    <button
      className={`${baseStyles} ${sizeStyles[size]} ${variantStyles[variant]} ${className}`}
      {...props}
    >
      {children}
    </button>
  );
};

export default Button;
