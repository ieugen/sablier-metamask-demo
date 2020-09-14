(ns sablier-metamask-demo.ethers
  (:require ["ethers" :as eth]))

(defn ethereum-provider
  "Receives window.ethereum and buidls an ethers.js Web3Provider"
  [ethereum]
  (new eth/ethers.providers.Web3Provider ethereum))


(comment

  ;; const provider = new ethers.providers.Web3Provider(window.ethereum)
  (let [provider (ethereum-provider (.-ethereum js/window))]
    (js/console.log provider))

  0)