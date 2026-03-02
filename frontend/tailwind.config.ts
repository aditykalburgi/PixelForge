import type { Config } from 'tailwindcss'

const config: Config = {
  content: [
    './index.html',
    './src/**/*.{js,ts,jsx,tsx}',
  ],
  theme: {
    extend: {
      // Colors: Strictly Monochrome
      colors: {
        background: '#FFFFFF',
        foreground: '#000000',
        muted: '#F5F5F5',
        'muted-foreground': '#525252',
        accent: '#000000',
        'accent-foreground': '#FFFFFF',
        border: '#000000',
        'border-light': '#E5E5E5',
        card: '#FFFFFF',
        'card-foreground': '#000000',
        ring: '#000000',
      },

      // Typography System
      fontFamily: {
        display: ['"Playfair Display"', 'Georgia', 'serif'],
        serif: ['"Source Serif 4"', 'Georgia', 'serif'],
        mono: ['"JetBrains Mono"', 'monospace'],
      },

      // Extended Type Scale (9xl = 160px)
      fontSize: {
        xs: '0.75rem',
        sm: '0.875rem',
        base: '1rem',
        lg: '1.125rem',
        xl: '1.25rem',
        '2xl': '1.5rem',
        '3xl': '2rem',
        '4xl': '2.5rem',
        '5xl': '3.5rem',
        '6xl': '4.5rem',
        '7xl': '6rem',
        '8xl': '8rem',
        '9xl': '10rem',
      },

      // Line Heights
      lineHeight: {
        'none': '1',
        'tight': '1.25',
        'snug': '1.375',
        'normal': '1.5',
        'relaxed': '1.625',
        'loose': '2',
      },

      // Letter Spacing
      letterSpacing: {
        'tightest': '-0.05em',
        'tighter': '-0.025em',
        'tight': '-0.025em',
        'normal': '0em',
        'wide': '0.025em',
        'wider': '0.05em',
        'widest': '0.1em',
      },

      // Border Radius: ALL ZEROS
      borderRadius: {
        'none': '0px',
        'DEFAULT': '0px',
      },

      // Borders & Lines
      borderWidth: {
        'hairline': '1px',
        'DEFAULT': '1px',
        'thin': '1px',
        'medium': '2px',
        'thick': '4px',
        'ultra': '8px',
      },

      // Shadows: NONE
      boxShadow: {
        'none': 'none',
        'DEFAULT': 'none',
      },

      // Container
      maxWidth: {
        'container': '1152px',
      },

      // Spacing enhancements
      spacing: {
        'prose': '80ch',
      },

      // Animation - minimal, instant
      transitionDuration: {
        'DEFAULT': '0ms',
        '0': '0ms',
        '100': '100ms',
      },

      // Opacity for texture patterns
      opacity: {
        '1': '.01',
        '2': '.02',
        '3': '.03',
        '5': '.05',
        '15': '.015',
      },
    },
  },
}

export default config
