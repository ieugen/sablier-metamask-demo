(ns sablier-metamask-demo.views
  (:require
   [re-frame.core :as re-frame]
   [sablier-metamask-demo.subs :as subs]))


(defn ethereum-panel []
  (let [ethereum-present? (re-frame/subscribe [::subs/ethereum-present?])]
    (if @ethereum-present?
      [:div
       [:h1 (str "Ethereum is present.")]
       [:p "Congratulations. No need to install "
        [:a {:href "https://metamask.io/" :target "_blank"} "MetaMask"]]]
       ;; Missing ethereum
      [:div [:h1 "Ethereum is missing."]
       [:p "You need to install " [:a {:href "https://metamask.io/" :target "_blank"} "MetaMask"]]])))

;; home

(defn home-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1 (str "Hello from " @name "...")]
     [:img {:src "https://i.giphy.com/media/14cAD9mBjofP5m/giphy.webp" :alt "Howdy"}]
     [ethereum-panel]
     [:div
      [:a {:href "#/about"}
       "go to About Page"]]]))

;; about

(defn about-panel []
  [:div
   [:h1 "This is the About Page."]

   [:div
    [:a {:href "#/"}
     "go to Home Page"]]])


;; main

(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    :about-panel [about-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [show-panel @active-panel]))
