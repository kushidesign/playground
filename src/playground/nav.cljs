(ns playground.nav
  (:require
   [kushi.ui.title.core :refer [title]]
   [playground.links :refer [links]]
   [playground.ui :refer [light-dark-mode-switch]]
   [kushi.core :refer [sx]]))

(defn kushi-title []
  [title (sx 'playground-title
             :.xxlarge
             :>span:ai--baseline
             :md:pbs--:--vp-top-header-padding-with-offset)
   "Kushi"
   [:span
    (sx 'playground-title-version-number :.xxxsmall :mis--0.5rem)
    "v1.0.0-alpha"]])

(defn kushi-desktop-nav []
  [:nav (sx
         :.fixed
         :.flex-row-sb
         :d--none
         :md:d--flex
         :z--10
         :w--100%
         :bgc--white
         :ai--c
         :h--:--topnav-height
         :bbe--4px:solid:#efefef
         :p--0:1rem)
   [kushi-title]
   [links]])


(defn kushi-mobile-nav []
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
    [kushi-title]
    [:div (sx :.flex-row-fs)
     [links]
     [:span
      (sx :mis--0.75rem
          ["has-ancestor(.hide-lightswitch):d" :none])
      [light-dark-mode-switch]]]]])
