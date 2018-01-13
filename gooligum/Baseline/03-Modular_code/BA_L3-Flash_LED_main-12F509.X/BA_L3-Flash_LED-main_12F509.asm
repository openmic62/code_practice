;************************************************************************
;                                                                       *
;   Filename:      BA_L3-Flash_LED-main.asm                             *
;   Date:          26/1/12                                              *
;   File Version:  1.2                                                  *
;                                                                       *
;   Author:        David Meiklejohn                                     *
;   Company:       Gooligum Electronics                                 *
;                                                                       *
;************************************************************************
;                                                                       *
;   Architecture:  Baseline PIC                                         *
;   Processor:     12F508/509                                           *
;                                                                       *
;************************************************************************
;                                                                       *
;   Files required: delay10.asm     (provides W x 10 ms delay)          *
;                                                                       *
;************************************************************************
;                                                                       *
;   Description:    Lesson 3, example 3                                 *
;                                                                       *
;   Demonstrates how to call external modules                           *
;                                                                       *
;   Flashes a LED at approx 1 Hz                                        *
;   LED continues to flash until power is removed                       *
;                                                                       *
;************************************************************************
;                                                                       *
;   Pin assignments:                                                    *
;       GP1 = flashing LED                                              *
;                                                                       *
;************************************************************************


;	list     p=12F509             ; list directive to define processor
	#include <p12F509.inc>        ; processor specific variable definitions
	
;***** CONFIGURATION
	; ext reset, no code protect, no watchdog, int RC clock
	__CONFIG   _MCLRE_ON & _CP_OFF & _WDT_OFF & _IntRC_OSC

; '__CONFIG' directive is used to embed configuration word within .asm file.
; The lables following the directive are located in the respective .inc file. 
; See respective data sheet for additional information on configuration word.

;***** VARIABLE DEFINITIONS *****************************************************
	UDATA
dc1     res 1                     ; delay loop counters
dc2     res 1                     
dc3     res 1                    
	
;***** RC CALIBRATION **********************************************************
RCCAL   CODE    0x0FF             ; processor reset vector
        res 1                     ; holds internal RC cal value, as a movlw k

;***** RESET VECTOR ************************************************************
RESET	CODE    0x000             ; effective reset vector
	movwf   OSCCAL            ; apply internal RC factory calibration 

; Internal RC calibration value is placed at location 0xFF by Microchip
; as a movlw k, where the k is a literal value.
			
;***** MAIN PROGRAM ************************************************************
	
;***** Initialization
start	
	movlw   b'111100'         ; configure GP1 (only) as an output
	tris    GPIO
		
;***** Main loop
main_loop
	; turn on LED
	movlw   b'000011'         ; set GP1 (bit 1)
	movwf   GPIO              ; and write to GPIO
	
	; delay 200 ms
	movlw   .20               ; delay 20 x 10 mS = 200 mS
	call    delay10xW         ; run the delay
	
	; turn off LED
	clrf    GPIO              ; (clearing GPIO clears GP1)

	; delay 800 ms
	movlw   .80               ; delay 80 x 10 mS = 800 mS
	call    delay10xW         ; run the delay
	
	; repeat forever
	goto    main_loop         ; repeat forever

;***** SUBROUTINES *************************************************************

;***** Variable delay: 10 mS to 2.55 S
delay10xW
	movwf   dc3               ; delay = 1+Wx(3+10009+3)-1+4 = W x 10.15 mS
dly2	movlw   .13               ; repeat inner loop 13 times
	movwf   dc2               ; -> 13x(767+3)-1 = 10009 cycles
	clrf    dc1               ; inner loop = 256x3-1 = 767 cycles
dly1	decfsz  dc1,f
	goto    dly1
	decfsz  dc2,f             ; end middle loop
	goto    dly1
	decfsz  dc3,f             ; end outer loop
	goto    dly2
	
	retlw   0

	END
