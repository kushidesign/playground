(ns playground.nav
  (:require
   [kushi.ui.title.core :refer [title]]
   [playground.links :refer [links]]
   [playground.ui :refer [light-dark-mode-switch]]
   [kushi.core :refer [sx]]))



(defn kushi-mobile-nav [{:keys [site-header display-kushi-links-in-mobile-nav?]}]
  [:div (sx
         'kushi-playground-mobile-nav
         :.transition
         :.xlarge
         :.flex-row-c
         :.fixed
         :zi--100
         :md:d--none
         :w--100%
         :padding-inline--:--page-padding-inline
         :padding-block--0.7em
         :bb--:--divisor
         :bc--black
         :dark:bc--white
         :bgc--black
         :dark:bgc--white
         :&_.playground-title:c--white
         :dark:&_.playground-title:c--black
         [:&_.project-links:filter "invert() brightness(2)"]
         [:dark:&_.project-links:filter "invert(0) brightness(2)"])
   [:div
    (sx :.extra :.flex-row-sb :w--:--components-menu-width)
    [site-header]
    [:div (sx :.flex-row-fs)
     (when display-kushi-links-in-mobile-nav?
       [links])
     [:span
      (sx :mis--0.75rem
          ["has-ancestor(.hide-lightswitch):d" :none])
      [light-dark-mode-switch]]]]])
