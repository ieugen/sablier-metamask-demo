(ns sablier-metamask-demo.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub ::name
                  (fn [db]
                    (:name db)))

(re-frame/reg-sub ::active-panel
                  (fn [db _]
                    (:active-panel db)))

(re-frame/reg-sub ::web3-enabled?
                  (fn [db]
                    (:web3-enabled? db)))

(re-frame/reg-sub ::web3-tested?
                  (fn [db]
                    (:web3-tested? db)))