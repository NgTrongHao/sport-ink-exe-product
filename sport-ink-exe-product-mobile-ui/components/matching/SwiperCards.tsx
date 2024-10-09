import { View, Text, StyleSheet } from "react-native";
import React, { useRef } from "react";
import Swiper from "react-native-deck-swiper";
import MatchingCard from "./MatchingCard";
import Ionicons from "@expo/vector-icons/Ionicons";
import { MatchingCardModel } from "@/models/User";

interface SwiperCardsProps {
  cards: MatchingCardModel[];
}

const SwiperCards = ({ cards }: SwiperCardsProps) => {
  const swiperRef = useRef<Swiper<any>>(null);

  const onSwipeLeft = () => {
    swiperRef.current && swiperRef.current.swipeLeft();
  };

  const onSwipeRight = () => {
    swiperRef.current && swiperRef.current.swipeRight();
  };

  return (
    <>
      <View className="flex-1 -mt-8">
        <Swiper
          ref={swiperRef}
          cards={cards}
          renderCard={(card: MatchingCardModel) => (
            <MatchingCard
              cardInfo={card}
              onSwipeLeft={onSwipeLeft}
              onSwipeRight={onSwipeRight}
            />
          )}
          stackSize={5}
          cardIndex={0}
          verticalSwipe={false}
          animateCardOpacity
          backgroundColor="transparent"
          stackSeparation={15}
          stackScale={5}
          overlayLabels={{
            left: {
              title: "NOPE",
              element: (
                <View style={styles.overlayLabel}>
                  <Ionicons name="close-circle" size={50} color="#ff6347" />
                  <Text style={styles.overlayLabelText}>NOPE</Text>
                </View>
              ),
              style: {
                wrapper: {
                  flexDirection: "column",
                  alignItems: "flex-end",
                  justifyContent: "flex-start",
                  marginTop: 30,
                  marginLeft: -30,
                },
              },
            },
            right: {
              title: "LIKE",
              element: (
                <View style={styles.overlayLabel}>
                  <Ionicons name="heart-circle" size={50} color="#32cd32" />
                  <Text style={styles.overlayLabelText}>LIKE</Text>
                </View>
              ),
              style: {
                wrapper: {
                  flexDirection: "column",
                  alignItems: "flex-start",
                  justifyContent: "flex-start",
                  marginTop: 30,
                  marginLeft: 30,
                },
              },
            },
          }}
        />
      </View>

      {/* Control Buttons */}
      <View className="flex flex-row justify-evenly px-4 mb-4">
        <View>
          <Ionicons
            name="close-circle"
            size={60}
            color="#ff6347"
            onPress={() => onSwipeLeft()}
          />
        </View>
        <View>
          <Ionicons
            name="heart-circle"
            size={60}
            color="#32cd32"
            onPress={() => onSwipeRight()}
          />
        </View>
      </View>
    </>
  );
};

const styles = StyleSheet.create({
  overlayLabel: {
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "center",
    padding: 10,
    borderRadius: 10,
    backgroundColor: "rgba(0, 0, 0, 0.5)",
  },
  overlayLabelText: {
    fontSize: 24,
    fontWeight: "bold",
    color: "#fff",
    marginLeft: 10,
  },
});

export default SwiperCards;
