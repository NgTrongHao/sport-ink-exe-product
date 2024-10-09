import React from "react";
import { Tabs } from "expo-router";
import { TabBarIcon } from "@/components/navigation/TabBarIcon";
import Header from "@/components/layout/Header";

const TabLayout = () => {
  return (
    <Tabs
      screenOptions={{
        headerShown: true,
        tabBarShowLabel: false, // Hide tab labels
        tabBarActiveTintColor: "#d97706", // Active tab icon color
        tabBarInactiveTintColor: "#555", // Inactive tab icon color
        tabBarStyle: {
          backgroundColor: "#fffbeb", // Tab bar background color
          shadowOffset: { width: 1, height: 1 },
          height: 60,
          borderTopWidth: 0,
        },
        header: () => <Header />,
      }}
    >
      <Tabs.Screen
        name="(home)"
        options={{
          title: "Home",
          tabBarIcon: ({ color, focused }) => (
            <TabBarIcon
              name={focused ? "extension-puzzle" : "extension-puzzle-outline"}
              color={color}
              size={25}
            />
          ),
        }}
      />
      <Tabs.Screen
        name="explore"
        options={{
          title: "Explore",
          tabBarIcon: ({ color, focused }) => (
            <TabBarIcon
              name={focused ? "map" : "map-outline"}
              color={color}
              size={25}
            />
          ),
        }}
      />
      <Tabs.Screen
        name="booking"
        options={{
          title: "Booking",
          tabBarIcon: ({ color, focused }) => (
            <TabBarIcon
              name={focused ? "today" : "today-outline"}
              color={color}
              size={40}
              style={{
                shadowOffset: { width: 1, height: 1 },
                fontWeight: "bold",
              }}
            />
          ),
        }}
      />
      <Tabs.Screen
        name="chat"
        options={{
          title: "Chat",
          tabBarIcon: ({ color, focused }) => (
            <TabBarIcon
              name={focused ? "chatbubbles" : "chatbubbles-outline"}
              color={color}
              size={25}
            />
          ),
        }}
      />
      <Tabs.Screen
        name="profile"
        options={{
          title: "Profile",
          tabBarIcon: ({ color, focused }) => (
            <TabBarIcon
              name={focused ? "person-circle" : "person-circle-outline"}
              color={color}
              size={25}
            />
          ),
        }}
      />
    </Tabs>
  );
};

export default TabLayout;
