# Minimalist Monochrome Design System Integration

## Overview

Your PixelForge frontend has been successfully integrated with the **Minimalist Monochrome** design system. This is a comprehensive transformation that includes:

- Tailwind CSS v4 with custom configuration
- Reusable UI components (Button, Card, Input, Separator)
- Redesigned pages (Login, Dashboard, ProjectList, ProjectDetail)
- Design tokens for typography, colors, spacing, and patterns
- Texture patterns and visual effects
- Accessibility-first focus states

---

## Tech Stack

- **Framework**: React 19 with TypeScript
- **Styling**: Tailwind CSS v4 + PostCSS
- **Build Tool**: Vite
- **Icons**: Lucide React (with thin stroke weight 1.5)
- **Typography**:
  - Display: Playfair Display (serif)
  - Body: Source Serif 4 (serif)
  - Mono: JetBrains Mono (monospace)

---

## File Structure

```
frontend/
├── src/
│   ├── components/
│   │   ├── ui/                          # Reusable components
│   │   │   ├── Button.tsx
│   │   │   ├── Card.tsx
│   │   │   ├── Input.tsx
│   │   │   ├── Separator.tsx
│   │   │   └── index.ts
│   │   ├── Layout.tsx                   # Main layout with sidebar
│   │   └── Sidebar.tsx                  # Navigation sidebar
│   ├── pages/
│   │   ├── Login.tsx                    # Redesigned login page
│   │   ├── Dashboard.tsx                # Redesigned dashboard
│   │   ├── ProjectList.tsx              # Redesigned project list
│   │   └── ProjectDetail.tsx            # Redesigned project detail
│   ├── index.css                        # Global styles + patterns
│   └── App.tsx                          # Updated app routing
├── tailwind.config.ts                   # Design tokens configuration
├── postcss.config.js                    # PostCSS configuration
└── package.json                          # Updated dependencies
```

---

## Design Tokens

### Colors (Strictly Monochrome)

```
Background:    #FFFFFF (Pure white)
Foreground:    #000000 (Pure black)
Muted:         #F5F5F5 (Off-white)
Muted Text:    #525252 (Dark gray)
Border Light:  #E5E5E5 (Light gray)
```

**CSS Variables Available**:
- `--background`, `--foreground`, `--muted`, `--muted-foreground`
- `--border`, `--border-light`
- `--font-display`, `--font-serif`, `--font-mono`

### Typography Scale

| Size | Value | Use Case |
|------|-------|----------|
| 9xl | 10rem | Oversized statements |
| 8xl | 8rem | Display headlines |
| 7xl | 6rem | Hero headlines |
| 6xl | 4.5rem | Hero subheadings |
| 5xl | 3.5rem | Page titles |
| 4xl | 2.5rem | Section titles |
| 3xl | 2rem | Subheadings |
| 2xl | 1.5rem | Section intros |
| lg | 1.125rem | Preferred body text |
| base | 1rem | Minimum body text |

### Border Radius

**All values are `0px`** - No rounded corners anywhere. Sharp, 90-degree corners throughout.

### Spacing

Standard Tailwind spacing, but with generous padding/margins for "dramatic negative space":
- Sections: `py-12 md:py-16`
- Cards: `p-6 md:p-8`
- Headers: `py-12 md:py-16`

---

## Reusable Components

### Button

```tsx
import { Button } from '@/components/ui';

// Variants: primary | secondary | ghost
// Sizes: sm | md | lg

<Button variant="primary" size="lg">
  Sign In →
</Button>
```

**Primary**: Black background, white text. Inverts on hover.
**Secondary**: Transparent background, black border. Fills on hover.
**Ghost**: Text only. Underline appears on hover.

### Card

```tsx
import { Card } from '@/components/ui';

// Variants: default | inverted | borderless

<Card variant="default">
  Content here
</Card>
```

**Default**: White background with black border.
**Inverted**: Black background with white text. Inverts on hover.
**Borderless**: No border, minimal styling. Good for lists.

### Input

```tsx
import { Input } from '@/components/ui';

<Input
  type="text"
  label="USERNAME"
  placeholder="Enter your username"
  error={error}
/>
```

- Bottom border only (2px, thickens to 4px on focus)
- Uppercase label with tracking
- Italic placeholder text

### Separator

```tsx
import { Separator } from '@/components/ui';

// Thickness: thin | medium | thick | ultra

<Separator thickness="thick" />
```

Creates horizontal rules between sections with configurable thickness.

---

## Pattern Classes

These CSS classes add subtle texture to sections:

```tsx
// Horizontal lines (default)
<div className="pattern-lines">...</div>

// Grid pattern (for editorial sections)
<div className="pattern-grid">...</div>

// Diagonal lines (for timelines)
<div className="pattern-diagonal">...</div>

// Noise texture (paper-like quality)
<div className="pattern-noise">...</div>

// For dark backgrounds (inverted)
<div className="pattern-lines-inverted">...</div>
```

---

## Page Structure (New Design)

### Login Page
- Oversized hero typography (6xl/7xl)
- Centered form card with border
- Thick architectural dividers at top and bottom
- Subtle noise pattern background
- Editor footer with "Secure Authentication" label

### Dashboard
- Large title (5xl/6xl)
- Thick separators between sections
- Grid of project cards (hover inverts colors)
- Cards show icon, name, description, status, date
- Responsive: 1 column mobile, 2 columns tablet, 3 columns desktop

### Project List
- Editorial list layout (not a table)
- Each project is a full-width card
- Hover state: background tints, arrow animates
- Strong typography hierarchy
- Light dividers between items

### Project Detail
- Hero section with large title and drop cap
- Description has drop cap styling (boxed first letter)
- Two-column layout: Documents (2/3) and Team (1/3)
- Card-based sections with borders
- Document list with hover effects
- Team member list with borders and roles

---

## Styling Guidelines

### Hover Effects
```tsx
// Cards invert on hover
className="transition-all duration-100 hover:bg-foreground hover:text-background"

// Links underline
className="hover:underline"

// Icons animate
className="group-hover:translate-x-1 transition-transform duration-100"
```

### Focus States (Accessibility)
- All buttons: `3px solid outline` with `3px offset`
- All inputs: Border thickens from 2px to 4px
- All interactive elements use `focus-visible` (no mouse outline)

### Typography
```tsx
// Headings: Display font, tight tracking
<h1 className="font-display text-6xl font-bold tracking-tighter">

// Body: Serif font, relaxed leading
<p className="font-serif text-base leading-relaxed">

// Labels: Mono font, widest tracking
<label className="font-mono text-xs tracking-widest uppercase">
```

### Motion
- Transitions: 0ms or 100ms maximum
- No bouncing, floating, or parallax
- Instant state changes preferred
- Minimal easing (mostly `duration-100`)

---

## Customization Examples

### Add a New Page with Minimalist Style

```tsx
import { Button, Card, Separator } from '@/components/ui';

export const NewPage: React.FC = () => {
  return (
    <div className="relative pattern-lines">
      {/* Header */}
      <header className="px-6 md:px-12 py-12 md:py-16">
        <h1 className="font-display text-5xl md:text-6xl font-bold tracking-tighter">
          Page Title
        </h1>
      </header>

      <Separator thickness="thick" className="mx-6 md:mx-12" />

      {/* Content */}
      <section className="px-6 md:px-12 py-12 md:py-16">
        <Card variant="default">
          <h2 className="font-display text-3xl font-bold tracking-tight mb-4">
            Section Title
          </h2>
          <p className="font-serif text-base leading-relaxed text-muted-foreground">
            Your content here.
          </p>
        </Card>
      </section>
    </div>
  );
};
```

### Modify Colors

Edit `tailwind.config.ts` under `theme.extend.colors`:

```ts
colors: {
  background: '#FFFFFF',    // Change here
  foreground: '#000000',    // Change here
  // ... other colors
}
```

**Important**: The design requires pure black/white. Grays are for secondary text/borders only.

### Add Custom Textures

In `index.css`, create new pattern classes:

```css
.pattern-custom {
  background-image: linear-gradient(
    45deg,
    transparent,
    transparent 40px,
    rgba(0, 0, 0, 0.05) 40px,
    rgba(0, 0, 0, 0.05) 50px
  );
}
```

---

## Responsive Behavior

- **Mobile** (< 768px): Single column, reduced text sizes (5xl → 3xl)
- **Tablet** (768px - 1024px): 2-column grids, medium text sizes
- **Desktop** (> 1024px): Full 3-column grids, oversized typography

Use Tailwind's `md:` and `lg:` prefixes:

```tsx
<h1 className="text-5xl md:text-6xl lg:text-7xl">
  Responsive Heading
</h1>
```

---

## Accessibility Checklist

✓ Black on white: 21:1 contrast ratio (WCAG AAA)
✓ Focus states on all interactive elements
✓ Touch targets: minimum 44px on mobile
✓ Semantic HTML (buttons, links, form labels)
✓ Icon stroke width 1.5 for visibility
✓ Alt text for images (if added)

---

## Development Tasks

### To Run the Dev Server
```bash
cd frontend
npm run dev
```
Then open `http://localhost:5173`

### To Build for Production
```bash
cd frontend
npm run build
```

### To Lint Code
```bash
npm run lint
```

---

## Next Steps

1. **Test all pages** in the browser to verify the design
2. **Add images** using the design system (borders, hover effects)
3. **Extend components** for any new features (modals, menus, etc.)
4. **Customize colors** if brand guidelines change
5. **Add animations** only where necessary (be minimal)
6. **Test accessibility** with keyboard navigation and screen readers

---

## Key Design Principles to Remember

1. **Monochrome Only**: No accent colors, gradients, or color variations
2. **Sharp Corners**: Zero border radius everywhere
3. **Oversized Type**: Headlines should dominate the space
4. **Dramatic Whitespace**: Generous padding and margins
5. **Lines Over Shapes**: Use borders and rules, not filled shapes
6. **Instant Feedback**: Transitions max 100ms
7. **Typography First**: Type is the primary visual element
8. **Texture Subtly**: Patterns add depth without being loud

---

## Support

For questions about Tailwind CSS: https://tailwindcss.com/docs
For Lucide icons: https://lucide.dev/
For design system philosophy: See the original design system document

Enjoy your new minimalist aesthetic!
