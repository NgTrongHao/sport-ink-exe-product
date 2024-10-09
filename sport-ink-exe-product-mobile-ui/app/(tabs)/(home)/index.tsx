import { SafeAreaView, ActivityIndicator } from "react-native";
import React, { useEffect, useState } from "react";
import SwiperCards from "@/components/matching/SwiperCards";
import { MatchingCardModel } from "@/models/User";

const CARDS_DATA: MatchingCardModel[] = [
  {
    id: 1,
    name: "Rubber Duck",
    profileImage: "https://www.artgroup.com/assets/img/products/WDC90568",
    age: 1,
    bio: "I am a rubber duck.\nIg: @rubberduck \nTwitter: @rubberduck \nSnapchat: @rubberduck",
    location: "Duckland",
    distance: 2,
    images: [
      "https://www.artgroup.com/assets/img/products/WDC90568",
      "https://www.artgroup.com/assets/img/products/WDC90568",
      "https://www.artgroup.com/assets/img/products/WDC90568",
      "https://www.artgroup.com/assets/img/products/WDC90568",
      "https://www.artgroup.com/assets/img/products/WDC90568",
      "https://www.artgroup.com/assets/img/products/WDC90568",
    ],
  },
  {
    id: 2,
    name: "Rubber Duck",
    profileImage: "https://www.artgroup.com/assets/img/products/WDC90568",
    age: 2,
    bio: "I am a rubber duck",
    location: "Duckland",
  },
  {
    id: 3,
    name: "Rubber Duck",
    profileImage: "https://www.artgroup.com/assets/img/products/WDC90568",
    age: 3,
    bio: "I am a rubber duck",
    location: "Duckland",
  },
  {
    id: 4,
    name: "Rubber Duck",
    profileImage: "https://www.artgroup.com/assets/img/products/WDC90568",
    age: 4,
    bio: "I am a rubber duck",
    location: "Duckland",
  },
  {
    id: 5,
    name: "Rubber Duck",
    profileImage: "https://www.artgroup.com/assets/img/products/WDC90568",
    age: 5,
    bio: "I am a rubber duck",
    location: "Duckland",
  },
  {
    id: 6,
    name: "Rubber Duck",
    profileImage: "https://www.artgroup.com/assets/img/products/WDC90568",
    age: 6,
    bio: "I am a rubber duck",
    location: "Duckland",
  },
  {
    id: 7,
    name: "Rubber Duck",
    profileImage: "https://www.artgroup.com/assets/img/products/WDC90568",
    age: 7,
    bio: "I am a rubber duck",
    location: "Duckland",
  },
  {
    id: 8,
    name: "Rubber Duck",
    profileImage: "https://www.artgroup.com/assets/img/products/WDC90568",
    age: 8,
    bio: "I am a rubber duck",
    location: "Duckland",
  },
  {
    id: 9,
    name: "Rubber Duck",
    profileImage: "https://www.artgroup.com/assets/img/products/WDC90568",
    age: 9,
    bio: "I am a rubber duck",
    location: "Duckland",
  },
  {
    id: 10,
    name: "Rubber Duck",
    profileImage: "https://www.artgroup.com/assets/img/products/WDC90568",
    age: 10,
    bio: "I am a rubber duck",
    location: "Duckland",
  },
];

const HomeScreen = () => {
  const [loading, setLoading] = useState(true);
  const [cards, setCards] = useState<MatchingCardModel[]>([]);

  useEffect(() => {
    try {
      setTimeout(() => {
        setCards(CARDS_DATA);
      }, 2000);
    } catch (error) {
      console.log("Error: ", error);
    } finally {
      setLoading(false);
    }
  }, []);

  if (loading) {
    return (
      <SafeAreaView className="flex-1 justify-center items-center">
        <ActivityIndicator size="large" color="#d97706" />
      </SafeAreaView>
    );
  }

  return (
    <SafeAreaView className="flex-1 pt-4">
      {/* Swiper Cards */}
      <SwiperCards cards={cards} />
    </SafeAreaView>
  );
};

export default HomeScreen;
