import { LocalNotifications } from '@capacitor/local-notifications';

export async function requestPermission() {
  try {
    const { display } = await LocalNotifications.requestPermissions();
    return display === 'granted';
  } catch(e) {
    console.log('Notification permission error:', e);
    return false;
  }
}

export async function sendNotification(title, body) {
  try {
    await LocalNotifications.schedule({
      notifications: [{
        title,
        body,
        id: Math.floor(Math.random() * 10000),
        sound: 'default',
        smallIcon: 'ic_launcher',
        iconColor: '#FF9933',
      }]
    });
  } catch(e) {
    console.log('Send notification error:', e);
  }
}

export async function scheduleDailyReminder(hour, minute) {
  try {
    await LocalNotifications.cancel({ notifications: [{ id: 9999 }] });
    await LocalNotifications.schedule({
      notifications: [{
        title: "Vaibhav — Time to Study! 📚",
        body: "Your UPSC journey continues today. Open Vaibhav and complete your tasks.",
        id: 9999,
        schedule: {
          on: { hour, minute },
          repeats: true,
        },
        sound: 'default',
        smallIcon: 'ic_launcher',
        iconColor: '#FF9933',
      }]
    });
    return true;
  } catch(e) {
    console.log('Schedule reminder error:', e);
    return false;
  }
}

export async function cancelDailyReminder() {
  try {
    await LocalNotifications.cancel({ notifications: [{ id: 9999 }] });
  } catch(e) {
    console.log('Cancel reminder error:', e);
  }
}