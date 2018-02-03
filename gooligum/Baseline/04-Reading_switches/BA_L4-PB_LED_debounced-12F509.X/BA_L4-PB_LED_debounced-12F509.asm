;************************************************************************
;                                                                       *
;   Filename:      BA_L4-PB_LED_no_debounce-12F509.asm                  *
;   Date:          02/02/2018                                           *
;   File Version:  1.1                                                  *
;                                                                       *
;   Author:        Mike Rocha                                           *
;   Company:       RoCo, Inc.                                           *
;                                                                       *
;************************************************************************
;                                                                       *
;   Architecture:  Baseline PIC                                         *
;   Processor:     12F508/509                                           *
;                                                                       *
;************************************************************************
;                                                                       *
;   Files required: delay10xW-12F509.asm                                *
;                                                                       *
;************************************************************************
;                                                                       *
;   Description:    Lesson 4, p. 13a                                    *
;                                                                       *
;   Demonstrates how to watch a switch without debounce                 *
;                                                                       *
;                                                                       *
;************************************************************************
;                                                                       *
;   Pin assignments:                                                    *
;       GP1 = toggling LED                                              *
;                                                                       *
;************************************************************************


;	list     p=12F509             ; list directive to define processor
	#include <p12F509.inc>        ; processor specific variable definitions
	
	EXTERN   delay10xW_R          ; W x 10 mS delay

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
	movlw   b'111101'         ; configure GP1 (only) as an output
	tris    GPIO
	
;***** Main loop
main_loop
	; wait for button press
wait_dn btfsc   GPIO,3
	goto    wait_dn       
	
	; toggle the LED
	movf    sGPIO,W
	xorlw   b'00000010'       ; toggle GP1 bit
	movwf   sGPIO             ; write to shadow
	movwf   GPIO              ; write to port
	
	; wait 20 ms debounce delay
	movlw   .2
	pagesel delay10xW_R       
	call    delay10xW_R       ; delay 2 x 10 ms = 20 ms
	pagesel $
	
wait_up btfss   GPIO,3            ; if button is up (GP3 high)
	goto    wait_up
	
	; wait 20 ms debounce delay
	movlw   .2
	pagesel delay10xW_R       
	call    delay10xW_R       ; delay 2 x 10 ms = 20 ms
	pagesel $
	
	; repeat forever
	goto    main_loop         ; repeat forever

	END
