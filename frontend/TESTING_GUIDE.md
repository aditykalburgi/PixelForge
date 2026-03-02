# Frontend UI Troubleshooting & Testing Guide

## Dev Server Status
✓ **Running on**: http://localhost:5174/
✓ **Backend**: Running (Java processes detected)
✓ **Auth Timeout**: 5 seconds (won't hang if backend unavailable)

---

## What You Should See

### Login Page (http://localhost:5174)

**Visual Elements:**
- White background (full screen)
- Black text in serif font ("Playfair Display")
- Large "PixelForge" heading
- "Nexus Platform" subheading
- Two input fields:
  - Username field
  - Password field
- Black "Sign In" button (inverts to white on hover)

**Layout:**
- Center-aligned form card
- Approximately 400px wide
- ~3rem top/bottom padding

---

## If the Page is Not Showing

### 1. **Hard Refresh Browser**
   - Press `Ctrl + Shift + R` (Windows)
   - Or `Cmd + Shift + R` (Mac)
   - This clears the cache and reloads

### 2. **Check Browser Console for Errors**
   - Press `F12` to open Developer Tools
   - Go to **Console** tab
   - Look for any red error messages

### 3. **Verify Dev Server is Running**
   - Terminal should show: `VITE v7.3.1 ready in XXX ms`
   - Should see: `Local: http://localhost:5174/`

### 4. **Check Backend is Responsive**
   - Open new terminal
   - Run: `curl -v http://localhost:8080/api/auth/me`
   - Should get a response (even if 401 unauthorized)

### 5. **Clear Node Modules Cache**
   ```bash
   cd frontend
   rm -r node_modules
   npm install
   npm run dev
   ```

---

## Testing Login

### Dummy Test Credentials
Use any username/password to test (since backend handles validation):
- **Username**: testuser
- **Password**: testpass

The app will:
1. Send credentials to backend
2. Backend validates and sets session
3. Redirects to Dashboard on success
4. Shows error message on failure

---

## Expected User Flow

```
1. Load http://localhost:5174
   ↓
2. App checks authentication (5 second timeout)
   ↓
3a. If authenticated → Show Dashboard
3b. If not authenticated → Show Login page
   ↓
4. Enter credentials and click "Sign In"
   ↓
5. Send to backend: POST /api/login
   ↓
6. Backend validates
   ↓
7a. Valid → Redirect to /dashboard
7b. Invalid → Show error message
```

---

## Network Requests to Check

Open **Network** tab in Developer Tools (F12) and look for these:

### Request 1: Auth Check
- **URL**: `http://localhost:8080/api/auth/me`
- **Method**: GET
- **Status**: 401 (OK if not authenticated) or 200 (if authenticated)

### Request 2: Form submission
- **URL**: `http://localhost:8080/api/login`
- **Method**: POST
- **Headers**: `Content-Type: application/x-www-form-urlencoded`
- **Body**: `username=...&password=...`

If requests are failing with **CORS errors**, the backend needs CORS configuration.

---

## Environment Details

- Node version: `v24.x.x` (check with `node --version`)
- npm version: `v11.x.x` (check with `npm --version`)
- Vite: 7.3.1
- React: 19.2.0
- TypeScript: 5.9.3

---

## Useful Commands

### Hot Reload Dev Server
```bash
cd frontend
npm run dev
```

### Build for Production
```bash
npm run build
```

### Start Backend (if needed)
```bash
cd ..  # Go to parent directory
npm run java:start
# or
mvn spring-boot:run
```

### Check Backend Health
```bash
curl http://localhost:8080/health
# or
curl http://localhost:8080/api/auth/me
```

---

## CSS/Styling Notes

### Current Setup
- Login uses **inline styles** (works immediately)
- Other pages use **Tailwind CSS** utility classes
- All styles should load from browser cache

### If Styles are Missing
- Check browser DevTools → **Network** tab
- Look for CSS file (should be `index-*.css`)
- If CSS isn't loading, restart dev server: `npm run dev`

---

## Quick Fixes Checklist

- [ ] Hard refresh browser (Ctrl+Shift+R)
- [ ] Verify dev server is running (`http://localhost:5174/`)  
- [ ] Check browser console for errors (F12 → Console)
- [ ] Verify backend is running on port 8080
- [ ] Check network tab for failed requests
- [ ] Restart dev server: `Ctrl+C` then `npm run dev`
- [ ] Clear node modules and reinstall: `rm -r node_modules && npm install`
- [ ] Restart entire application

---

## Contact Backend to Verify It's Running

```bash
# Test endpoint
curl -i http://localhost:8080/api/auth/me

# Expected response (if not auth'd):
# HTTP/1.1 401 Unauthorized
# Content-Length: 0
```

---

## File Locations for Reference

- **Frontend**: `c:\Users\adity\OneDrive\Desktop\PixelForge\frontend\`
- **Backend**: `c:\Users\adity\OneDrive\Desktop\PixelForge\`
- **Login Page**: `frontend\src\pages\Login.tsx`
- **Auth Context**: `frontend\src\context\AuthContext.tsx`
- **API Config**: `frontend\src\api\axios.ts`

---

## Next Steps

1. **Load the app** at http://localhost:5174/
2. **Verify login page appears** with:
   - "PixelForge" heading
   - Input fields
   - Sign In button
3. **Test login** with any credentials
4. **Check dashboard** loads after successful login
5. **Review Network tab** if anything fails

If issues persist, check browser console and network tab for specific error messages.
