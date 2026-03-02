import React from 'react';

interface SeparatorProps {
  thickness?: 'thin' | 'medium' | 'thick' | 'ultra';
  className?: string;
}

const Separator: React.FC<SeparatorProps> = ({
  thickness = 'thick',
  className = '',
}) => {
  const thicknessStyles = {
    thin: 'h-[1px] my-6',
    medium: 'h-[2px] my-8',
    thick: 'h-[4px] my-12',
    ultra: 'h-[8px] my-16',
  };

  return (
    <div
      className={`bg-foreground ${thicknessStyles[thickness]} ${className}`}
    />
  );
};

export default Separator;
