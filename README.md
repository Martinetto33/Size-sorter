# Size-sorter

Assuming a stream of random sizes (XXS, IT 50, 43, UK 12, FR 20, 19, XL, L, M...) is provided, find a way to sort the sizes.

This project was done for an interview at a mobile-development company. Kotlin language was used.

The idea I implemented here was to create a system of conversion maps from sizes of a region to sizes of another region.
What I was told is that this system was far too complicated, and it involved frequent changes of existing classes
in case a new unrecognised size was provided by the stream (e.g., size 'O' or 'ES 12').

A simpler approach could have been sorting all sizes by size name (XS, L, M...), by country + size number in lexicographic
order, by size numbers only (18, 40, ...) and by unknown formats separately.