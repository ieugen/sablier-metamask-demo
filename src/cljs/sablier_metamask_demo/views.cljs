(ns sablier-metamask-demo.views
  (:require
   [re-frame.core :as re-frame]
   [reagent.core :as r]
   [sablier-metamask-demo.subs :as subs]
   [sablier-metamask-demo.events :as events]))

(defn meta-mask-link [] [:a {:href "https://metamask.io/" :target "_blank"} "MetaMask"])

(defn check-ethereum-panel []
  [:div
   [:p "Before we go, we need to check if you have MetaMask browser extension installed"]
   [:button {:type "button"
             :id "check-web3-btn"
             :on-click #(re-frame/dispatch [::events/check-web3 js/window])} "Check MetaMask"]])  ;; <---

(defn ethereum-installed-panel []
  (let [web3-enabled? (re-frame/subscribe [::subs/web3-enabled?])]
    (if @web3-enabled?
      [:div
       [:h1 (str "Ethereum is present.")]
       [:p "Congratulations. No need to install " [meta-mask-link]]]
 ;; Missing ethereum
      [:div [:h1 "Ethereum is missing."]
       [:p "You need to install " [meta-mask-link]]])))

(defn ethereum-panel []
  (let [web3-tested? (re-frame/subscribe [::subs/web3-tested?])]
    (if @web3-tested?
      [ethereum-installed-panel]
      [check-ethereum-panel])))

(defn howdy-partner []
  (let [!ref (atom nil)]
    (r/create-class
     {:display-name "howdy-partner"
      :reagent-render (fn []
                        [:div
                         [:x-gif {:ref (fn [com] (reset! !ref com))
                                  :src "https://media.giphy.com/media/14cAD9mBjofP5m/giphy.gif"
                                  :alt (str "Howdy ")
                                  "n-times" 1}]
                         [:button {:on-click (fn [_] (.removeAttribute @!ref "stopped") )} "Howdy again"]])})))

;; home
(defn home-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1 (str "Hello from " @name "...")]
     [howdy-partner]
     [:p "This is a simple app that will allow you to connect to your "
      [meta-mask-link] " account and transfer some money to an address you chose"]
     [:p "(Spoiler alert: it will be mine. Always :D)."]
     [:p "So trust the app !"]
     [ethereum-panel]]))

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
