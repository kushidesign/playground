(ns playground.colors
  (:require
   [clojure.string :as string]
   ["tinycolor2" :as tinycolor]
   [playground.util :as util :refer-macros (keyed)]
   [kushi.core :refer (sx inject! defclass merge-attrs)]
   [kushi.ui.modal.core :refer [modal open-kushi-modal close-kushi-modal]]
   [kushi.ui.snippet.core :refer (copy-to-clipboard-button)]
   [kushi.ui.dom :refer (copy-to-clipboard)]
   [kushi.ui.core :refer [defcom]]
   [kushi.ui.icon.mui.core :refer [mui-icon]]
   [kushi.color :refer [base-color-map]]
   [kushi.colors :as kushi.colors]
   [kushi.ui.label.core :refer [label]]))

(defcom text-sample-sticker
  (let [{:keys [color bgc]} &opts]
    [label [:span (merge-attrs
                   (sx :.flex-row-c
                       :.small
                       :ai--c
                       :w--44px
                       :sm:w--54px
                       :h--44px
                       :sm:h--54px
                       [:c color]
                       [:bgc bgc]
                       :border-radius--50%
                       :mis--10px)
                   &attrs)
            "Text"]]))


(defn copy-color [s]
 [:span (sx :.flex-row-fs :ai--c)
  [:code s]
  [copy-to-clipboard-button
   {:on-click          #(copy-to-clipboard s)}]])

(defn color-modal
  [{:keys [k
           hsl
           color-name
           color-level]
    :as m}]
  [modal
   (sx :.flex-col-c
       {:-trigger     [label (sx :.pointer
                                 :.code
                                 :&.code:bgc--transparent
                                 :fs--0.7em
                                 :sm:fs--0.9em
                                 :ws--n
                                 {:on-click open-kushi-modal})
                       [:span (sx :sm:d--none) color-level]
                       [:span (sx :sm:d--block :d--none) k]
                       [mui-icon (sx :mis--0.5em) :help-outline]]
        :-scrim-attrs (sx ["dark:bgc" '(rgba 30 30 30 0.86)])
        :-panel-attrs (sx :h--600px
                          :max-width--100vw
                          :dark:bgc--black)})
   [:div
    (sx :.flex-col-sa
        :ai--c
        :h--100%
        :w--100%)
    [:div (sx :.huge
              :.normal
              :w--100px
              :h--100px
              [:bgc hsl])]
    (let [[s l]     (map #(-> % (string/replace #"\)$" "") string/trim)
                         (rest (string/split hsl #",")))
          hue-key   (as-> color-name $ (name $) (str "--" $ "-hue") (keyword $))
          color-obj (tinycolor #js {:h (hue-key base-color-map)
                                    :s s
                                    :l l})
          hex       (.toHexString color-obj)
          hsl       (.toHslString color-obj)
          rgb       (.toRgbString color-obj)]
      [:div
       (sx :d--grid
           :grid-gap--20px
           :ai--c
           :gtc--1fr:3fr)
       [:span.meta-desc-label "name"] [copy-color (string/replace (name k) #"^--" "")]
       [:span.meta-desc-label "token"] [copy-color (name k)]
       [:span.meta-desc-label "css var"] [copy-color (str "var(" (name k) ")")]
       [:span.meta-desc-label "hex"] [copy-color hex]
       [:span.meta-desc-label "hsl"] [copy-color hsl]
       [:span.meta-desc-label "rgb"] [copy-color rgb]])]] )


(defn color-rows [color-scales]
  [:<>
   (let [row-height   :75px
         kushi-colors (reduce (fn [acc [k v]]
                                (assoc acc (keyword k) v))
                              {}
                              (partition 2 kushi.colors/colors))]
     (for [{:keys [color-name scale]} color-scales
           :let [semantic-alias (some-> kushi-colors
                                        (get color-name)
                                        :alias)]]
       (into
        ^{:key color-name}
        [:div (sx 'color-scale-wrapper
                  :.transition
                  :dark:bgc--black
                  :dark:outline--1rem:solid:black
                  :mbs--85px)
         [:h3 (sx :.xlarge
                  :.wee-bold
                  :tt--capitalize)
          color-name]
         [:p (sx :.normal
                 :mb--1em:2.5em)
          "All "
          [:code (str "--" color-name)]
          " values on the scale have a corresponding "
          [:code (str "--" semantic-alias)]
          " alias token"]]
        (for [[k v color-level] scale
              :let  [hsl (if (number? v) (str v) (name v))]]
          ^{:key hsl}
          [:div (sx :.flex-row-fs
                    [:h row-height])
           [:div (sx :sm:flex-basis--150px
                     :width--66px
                     :sm:width--unset
                     :flex-grow--0
                     :flex-shrink--0
                     [:bgc hsl])]
           [:div (sx :.flex-row-sb
                     :flex-grow--1
                     :pis--0.5em
                     :bbes--solid
                     :bbew--1px
                     [:bbec hsl])
            [color-modal (keyed k hsl color-name color-level)]
            [:div (sx :.flex-row-fe)
             [text-sample-sticker (sx {:-color :white
                                       :-bgc   hsl})]
             [text-sample-sticker (sx {:-color :black
                                       :-bgc   hsl})]
             [text-sample-sticker (sx :bs--solid
                                      :bw--1px
                                      [:bc hsl]
                                      {:-color hsl
                                       :-bgc :white})]
             [text-sample-sticker (sx [:bc hsl]
                                      :bs--solid
                                      :bw--1px
                                      {:-bgc   :black
                                       :-color hsl})]]]]))))])
