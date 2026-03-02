import React from 'react';

interface CardProps {
  variant?: 'default' | 'inverted' | 'borderless';
  children: React.ReactNode;
  className?: string;
}

const Card: React.FC<CardProps> = ({
  variant = 'default',
  children,
  className = '',
}) => {
  const baseStyles = 'transition-all duration-100';

  const variantStyles = {
    default: 'bg-background border border-foreground text-foreground p-6 md:p-8',
    inverted: 'bg-foreground text-background border-0 p-6 md:p-8 hover:bg-background hover:text-foreground hover:border hover:border-foreground',
    borderless: 'bg-transparent border-0 text-foreground p-6 md:p-8',
  };

  return (
    <div className={`${baseStyles} ${variantStyles[variant]} ${className}`}>
      {children}
    </div>
  );
};

export default Card;
