;************************************************************************
;                                                                       *
;   Filename:      BA_L4-PB_LED_direct-12F509.asm                             *
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
	__CONFIG   _MCLRE_OFF & _CP_OFF & _WDT_OFF & _IntRC_OSC

; '__CONFIG' directive is used to embed configuration word within .asm file.
; The lables following the directive are located in the respective .inc file. 
; See respective data sheet for additional information on configuration word.

;***** VARIABLE DEFINITIONS *****************************************************
	UDATA_SHR
sGPIO   res 1                     ; shadow copy of GPIO
	
;***** RC CALIBRATION **********************************************************
RCCAL   CODE    0x3FF             ; processor reset vector
        res 1                     ; holds internal RC cal value, as a movlw k

;***** RESET VECTOR ************************************************************
RESET	CODE    0x000             ; effective reset vector
	movwf   OSCCAL            ; apply internal RC factory calibration 
		
;***** MAIN PROGRAM ************************************************************
	
;***** Initialization
start	
	movlw   b'111100'         ; configure GP1 (only) as an output
	tris    GPIO
	
;***** Main loop
main_loop
	; turn on LED only if button is pressed
	clrf    sGPIO             ; assume button up - LED off
	;movlw   b'11111111'
	movf    sGPIO,W
	
	btfss   GPIO,3            ; if button is pressed (GP3 low)
	bsf     sGPIO,1           ; turn on LED
	btfsc   GPIO,3            ; if button is pressed (GP3 low)
	bsf     sGPIO,0           ; turn on LED
	
	movf    sGPIO,W           ; and write to GPIO
	movwf   GPIO
	
	; repeat forever
	goto    main_loop         ; repeat forever

	END
