(ns sablier-metamask-demo.events
  (:require
   [re-frame.core :as re-frame]
   [sablier-metamask-demo.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced]]))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            db/default-db))

(re-frame/reg-event-db
 ::check-web3
 (fn-traced [db [_ ^js js-window]]
            (let [web3-enabled (exists? (.-ethereum js-window))]
              (merge db {:web3-enabled? web3-enabled
                         :web3-tested? true}))))

(re-frame/reg-event-db
 ::set-active-panel
 (fn-traced [db [_ active-panel]]
            (assoc db :active-panel active-panel)))

(comment

  (exists? (.-ethereum js/window)))

0