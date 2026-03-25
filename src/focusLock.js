import { registerPlugin } from '@capacitor/core';

// Register the native plugin
const FocusLockNative = registerPlugin('FocusLock');

// Check if running on Android
const isAndroid = typeof window.Capacitor !== 'undefined' && 
                  window.Capacitor.getPlatform() === 'android';

export async function startFocusLock() {
  if (isAndroid) {
    try {
      await FocusLockNative.startFocusLock();
      return true;
    } catch(e) {
      console.log('Focus lock error:', e);
      return false;
    }
  }
  return false;
}

export async function stopFocusLock() {
  if (isAndroid) {
    try {
      await FocusLockNative.stopFocusLock();
      return true;
    } catch(e) {
      console.log('Stop lock error:', e);
      return false;
    }
  }
  return false;
}
