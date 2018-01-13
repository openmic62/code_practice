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
	
	EXTERN   delay10xW_R          ; W x 10 mS delay

;***** CONFIGURATION
	; ext reset, no code protect, no watchdog, int RC clock
	__CONFIG   _MCLRE_ON & _CP_OFF & _WDT_OFF & _IntRC_OSC

; '__CONFIG' directive is used to embed configuration word within .asm file.
; The lables following the directive are located in the respective .inc file. 
; See respective data sheet for additional information on configuration word.

;***** VARIABLE DEFINITIONS *****************************************************
	UDATA
sGPIO   res 1                     ; shadow copy of GPIO
	
;***** RC CALIBRATION **********************************************************
RCCAL   CODE    0x0FF             ; processor reset vector
        res 1                     ; holds internal RC cal value, as a movlw k

;***** RESET VECTOR ************************************************************
RESET	CODE    0x000             ; effective reset vector
	movwf   OSCCAL            ; apply internal RC factory calibration 

	pagesel start             ; ensure the linker selects the correct page
	goto    start             ; jump around subroutine vectors
; Internal RC calibration value is placed at location 0xFF by Microchip
; as a movlw k, where the k is a literal value.
	
;***** Subroutine vectors
delay10xW
	pagesel delay10xW_R       ; ensure the linker selects the correct page
	goto    delay10xW_R       ; delay 10 mS x W times
		
;***** MAIN PROGRAM ************************************************************
MAIN    CODE
	
;***** Initialization
start	
	movlw   b'111100'         ; configure GP1 (only) as an output
	tris    GPIO
	
	banksel sGPIO
	movlw   b'111101'
	movwf   sGPIO             ; start with the shadow register zeroed
	
;***** Main loop
main_loop
	; toggle on LED at GP1
	movf    sGPIO,w           ; get shadow copy of GPIO
	xorlw   b'000011'         ; toggle bit corresponding to the GP1 (bit 1)
	movwf   sGPIO             ;   in shadow register
	movwf   GPIO              ; and write to GPIO
	
	; delay 500 ms
	movlw   .50               ; delay 50 x 10 mS = 5090 mS
	pagesel delay10xW         ;   -> 1Hz flashing 50% duty cycle
	call    delay10xW         ; run the delay

	; repeat forever
	pagesel main_loop
	goto    main_loop         ; repeat forever


	END
